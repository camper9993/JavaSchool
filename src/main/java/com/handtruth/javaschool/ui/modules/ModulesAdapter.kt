package com.handtruth.javaschool.ui.modules

import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.handtruth.javaschool.data.model.ModuleInfo
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast

import androidx.fragment.app.FragmentActivity
import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.ui.ModuleActivity
import com.handtruth.javaschool.ui.TestActivity

class ModulesAdapter(val activity: FragmentActivity, val viewModel: ModulesViewModel) : RecyclerView.Adapter<ModulesAdapter.ModuleInfoHolder>() {

    private var moduleInfoList = mutableListOf<DownloadedModules>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModuleInfoHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.module_list_item, parent, false)
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
        holder.dialogImgRes = moduleInfo.imgIdRes
        holder.id = moduleInfo.id
        SetImgAsyncTask(holder).execute(moduleInfo.imgIdRes)
    }

    fun setModuleInfo(moduleInfo: MutableList<DownloadedModules>) {
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
        var desc = "Hello world"
        var dialogImgRes: Int = 0
        var id = -1
        init {
            itemView.setOnClickListener {
                    val intent  = Intent(this@ModulesAdapter.activity, ModuleActivity::class.java)
                    intent.putExtra("MODULE_ID", id)
                    Log.d("TestAdapter", id.toString())
                    activity.startActivity(intent)
                }
            }
        }



    private class SetImgAsyncTask(private val holder: ModulesAdapter.ModuleInfoHolder) :
        AsyncTask<Int, Void, Int>() {

        override fun doInBackground(vararg integerRes: Int?): Int {
            return if (integerRes[0] == null) {
                R.mipmap.ic_launcher
            } else {
                integerRes[0]!!
            }
        }

        override fun onPostExecute(res: Int) {
            holder.img.setImageResource(res)
        }
    }
}


//    inner class CustomListener(val id: Int) : View.OnClickListener {
//        override fun onClick(v: View?) {
//            CapRepositoryProvider.provideFilesRepository().getModule(id)
//            Log.d("CustomListener", id.toString())
//            viewModel.delete(id)
//        }
//
//    }