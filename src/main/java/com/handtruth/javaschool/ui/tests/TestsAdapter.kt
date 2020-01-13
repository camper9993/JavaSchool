package com.handtruth.javaschool.ui.tests

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.handtruth.javaschool.data.model.ModuleInfo
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity

import com.handtruth.javaschool.R
import com.handtruth.javaschool.baseURL
import com.handtruth.javaschool.connect.RepositoryProvider
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.data.repo.ModuleTestsRepo
import com.handtruth.javaschool.data.repo.TestRepo
import com.handtruth.javaschool.ui.TestActivity
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TestsAdapter(val activity: FragmentActivity, val viewModel: TestsViewModel) : RecyclerView.Adapter<TestsAdapter.TestInfoHolder>() {

    private var moduleInfoList = mutableListOf<DownloadedModules>()
    private val moduleTestsRepo = ModuleTestsRepo(activity.application)
    private val testRepo = TestRepo(activity.application)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestInfoHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.module_tests_list_item, parent, false)
        return TestInfoHolder(itemView)
    }


    override fun getItemCount(): Int {
        return moduleInfoList.size
    }


    override fun onBindViewHolder(holder: TestInfoHolder, position: Int) {
        val moduleInfo = moduleInfoList[position]

        holder.name.text = moduleInfo.name
        holder.powerPointNumber.text = moduleInfo.powerPoints.toString()
        holder.complexityNumber.text = moduleInfo.complexity.toString()
        holder.desc = moduleInfo.description

        Picasso.with(activity.baseContext)
            .load(baseURL + "file/image/${moduleInfo.imgId}")
            .placeholder(R.drawable.download_logo)
            .error(R.drawable.loader_progress)
            .into(holder.img)

        holder.id = moduleInfo.id
        if (moduleInfo.isDownloaded) {
            holder.imgButton.visibility = View.GONE
        } else {
            holder.imgButton.setOnClickListener(CustomListener(moduleInfo, holder.progress))
        }
    }

    fun setTestInfo(moduleInfo: MutableList<DownloadedModules>) {
        this.moduleInfoList = moduleInfo
        Log.e("ADAPTER", (moduleInfo.toString()))
        notifyDataSetChanged()
    }


    inner class TestInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val powerPointNumber: TextView = itemView.findViewById(R.id.power_point_number)
        val complexityNumber: TextView = itemView.findViewById(R.id.complexity_number)
        var img: ImageView = itemView.findViewById(R.id.test_imageView)
        var imgButton: ImageButton = itemView.findViewById(R.id.image_button_tests)
        var progress: ProgressBar = itemView.findViewById(R.id.download_progress_bar)
        var desc = "Hello world"
        var id = -1

        init {
            itemView.setOnClickListener {
                if (imgButton.visibility == View.GONE) {
                    val intent  = Intent(this@TestsAdapter.activity, TestActivity::class.java)
                    intent.putExtra("MODULE_ID", id)
                    Log.d("TestAdapter", id.toString())
                    activity.startActivity(intent)
                } else {
                    Toast.makeText(activity, "Вам необходимо загрузить данные", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inner class CustomListener(val moduleInfo: DownloadedModules, private val progress: View) : View.OnClickListener {
        @SuppressLint("ShowToast")
        override fun onClick(v: View?) {
            val builder = AlertDialog.Builder(this@TestsAdapter.activity)
            builder.setTitle("Скачать выбранный курс?")
            builder.setPositiveButton("Скачать") { _: DialogInterface, _: Int ->
                RepositoryProvider.provideFilesRepository().getLessonTests(moduleInfo.id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        progress.visibility = View.VISIBLE
                        v!!.visibility = View.GONE
                        RepositoryProvider.provideFilesRepository().getTests(moduleInfo.id)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ tests ->
                                moduleInfo.isDownloaded = true

                                moduleTestsRepo.insertAll(result)
                                testRepo.insertAll(tests)

                                progress.visibility = View.GONE
                                viewModel.update(moduleInfo)

                                Log.d("CustomListener", "Добавление в базу данных!!!")
                            }, { error ->
                                v.visibility = View.VISIBLE
                                progress.visibility = View.GONE
                                Toast.makeText(activity.baseContext, "Не удалось загрузить тесты", Toast.LENGTH_SHORT)
                                error.printStackTrace()
                            })

                    }, { error ->
                        v!!.visibility = View.GONE
                        Toast.makeText(activity.baseContext, "Не удалось загрузить тесты", Toast.LENGTH_SHORT)
                        error.printStackTrace()
                    })
            }
            builder.setNegativeButton("Назад", null)
            val alert = builder.create()
            alert.show()
        }
    }
}