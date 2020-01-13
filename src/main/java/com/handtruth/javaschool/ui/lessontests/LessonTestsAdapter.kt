package com.handtruth.javaschool.ui.lessontests

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController


import com.handtruth.javaschool.R

import com.handtruth.javaschool.data.model.ModuleTests
class LessonTestsAdapter(val fragment: LessonTestsFragment, viewModel: LessonTestsViewModel) : RecyclerView.Adapter<LessonTestsAdapter.LessonTestsHolder>() {

    private var lessonTestsList = mutableListOf<ModuleTests>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LessonTestsHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.lesson_tests_list_item, parent, false)
        return LessonTestsHolder(itemView)
    }


    override fun getItemCount(): Int {
        return lessonTestsList.size
    }


    override fun onBindViewHolder(holder: LessonTestsHolder, position: Int) {
        val moduleTests = lessonTestsList[position]
        holder.name.text = moduleTests.name
        holder.count.text = moduleTests.count.toString()
        holder.id = moduleTests.id

    }

    fun setLessonTests(lessonTestsList: MutableList<ModuleTests>) {
        this.lessonTestsList = lessonTestsList
        notifyDataSetChanged()
    }


    inner class LessonTestsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.lesson_name)
        val count: TextView = itemView.findViewById(R.id.tests_count)
        var id = -1

        init {
            itemView.setOnClickListener {
                val action = LessonTestsFragmentDirections
                    .actionLessonTestsFragmentToSimpleTestFragment(id)
                Log.d("LESSON_ADAPTER", id.toString())
                this@LessonTestsAdapter.fragment
                    .findNavController()
                    .navigate(action)
            }
        }
    }

    inner class CustomListener(val id: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
        }
    }
}