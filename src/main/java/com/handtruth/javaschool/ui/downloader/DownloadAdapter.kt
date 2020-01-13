package com.handtruth.javaschool.ui.downloader

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.handtruth.javaschool.data.model.ModuleInfo
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.FragmentActivity
import com.handtruth.javaschool.R
import com.handtruth.javaschool.baseURL
import com.handtruth.javaschool.connect.RepositoryProvider
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.data.model.ModelViewReceiver
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DownloadAdapter(val activity: FragmentActivity, val viewModel: DownloadViewModel) : RecyclerView.Adapter<DownloadAdapter.ModuleInfoHolder>() {

    private var moduleInfoList = mutableListOf<ModuleInfo>()
    private val url = ""

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModuleInfoHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.store_list_item, parent, false)
        return ModuleInfoHolder(itemView)
    }


    override fun getItemCount(): Int {
        return moduleInfoList.size
    }


    override fun onBindViewHolder(holder: ModuleInfoHolder, position: Int) {
        val moduleInfo = moduleInfoList[position]

        holder.name.text = moduleInfo.name
        holder.lessonCountNumber.text = moduleInfo.lessonsCount.toString()
        holder.powerPointNumber.text = moduleInfo.powerPoints.toString()
        holder.complexityNumber.text = moduleInfo.complexity.toString()
        holder.desc = moduleInfo.description

        Picasso.with(activity.baseContext)
            .load(baseURL + "file/image/${moduleInfo.imgId}")
            .placeholder(R.drawable.download_logo)
            .error(R.drawable.loader_progress)
            .into(holder.img)

        holder.id = moduleInfo.id
        holder.imgButton.setOnClickListener(CustomListener(moduleInfo))
//        SetImgAsyncTask(holder).execute(moduleInfo.imgIdRes)
    }

    fun setModuleInfo(moduleInfo: MutableList<ModuleInfo>) {
        this.moduleInfoList = moduleInfo
        Log.e("ADAPTER", (moduleInfo.toString()))
        notifyDataSetChanged()
    }


    inner class ModuleInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val lessonCountNumber: TextView = itemView.findViewById(R.id.lesson_count_number)
        val powerPointNumber: TextView = itemView.findViewById(R.id.power_point_number)
        val complexityNumber: TextView = itemView.findViewById(R.id.complexity_number)
        var img: ImageView = itemView.findViewById(R.id.dialog_imageView)
        var imgButton: ImageButton = itemView.findViewById(R.id.imageButton)
        var desc = "Hello world"
        var dialogImgRes: Int = 0
        var id = -1

        init {
            itemView.setOnClickListener {
                Log.d("ADAPTER", "DIALOG!!! name: ${name.text}")

                val builder = AlertDialog.Builder(this@DownloadAdapter.activity)
                val viewGroup: ViewGroup? = null
                val inflater = activity.layoutInflater
                val view = inflater.inflate(R.layout.store_dialog, viewGroup)

                val lessonCountNum: TextView = view.findViewById(R.id.dialog_lesson_count_number)
                val powerPointNum: TextView = view.findViewById(R.id.dialog_power_point_number)
                val complexityNum: TextView = view.findViewById(R.id.dialog_complexity_number)
                val description: TextView = view.findViewById(R.id.description)
                val imgView: ImageView = view.findViewById(R.id.dialog_imageView)

                lessonCountNum.text = lessonCountNumber.text
                powerPointNum.text = powerPointNumber.text
                complexityNum.text = complexityNumber.text
                description.text = desc
                imgView.setImageResource(dialogImgRes)

                builder.setTitle(name.text)
                builder.setView(view)
                builder.setNegativeButton("Back", null)

                val alert = builder.create()
                alert.show()
            }
        }
    }


    inner class CustomListener(val moduleInfo: ModuleInfo) : View.OnClickListener {
        @SuppressLint("ShowToast")
        override fun onClick(v: View?) {
            val builder = AlertDialog.Builder(this@DownloadAdapter.activity)
            builder.setTitle("Скачать выбранный курс?")
            builder.setPositiveButton("Скачать") { _: DialogInterface, _: Int ->
                RepositoryProvider.provideFilesRepository().getModule(moduleInfo.id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ module ->
                        ModelViewReceiver.publishModuleInfo(moduleInfo)
                        ModelViewReceiver.publishModule(module)
                        viewModel.delete(moduleInfo)
                        Log.d("Result", "There are ${module}")
                    }, { error ->
                        Toast.makeText(activity.baseContext, "Не удалось загрузить модуль", Toast.LENGTH_SHORT)
                        error.printStackTrace()
                    })

            }
            builder.setNegativeButton("Назад", null)
            val alert = builder.create()
            alert.show()
            Log.d("CustomListener", moduleInfo.id.toString())
        }

    }
}