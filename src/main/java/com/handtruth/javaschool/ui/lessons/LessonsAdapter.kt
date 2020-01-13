package com.handtruth.javaschool.ui.lessons

import com.handtruth.javaschool.ui.lessontests.LessonTestsFragment
import com.handtruth.javaschool.ui.lessontests.LessonTestsViewModel
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController


import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.model.Lesson
import com.handtruth.javaschool.data.model.ModuleTests
import com.handtruth.javaschool.ui.lessontests.LessonTestsFragmentDirections

class LessonsAdapter(val fragment: LessonsFragment, viewModel: LessonsViewModel) : RecyclerView.Adapter<LessonsAdapter.LessonTestsHolder>() {

    private var lessonTestsList = mutableListOf<Lesson>()

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
        val lesson = lessonTestsList[position]
        holder.name.text = lesson.name
        holder.id = lesson.id
        holder.content = lesson.content
    }

    fun setLessonTests(lessonTestsList: MutableList<Lesson>) {
        this.lessonTestsList = lessonTestsList
        notifyDataSetChanged()
    }


    inner class LessonTestsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.lesson_name)
        var id = -1
        var content = " "
        init {
            itemView.setOnClickListener {
                val action = LessonsFragmentDirections
                    .actionLessonsFragmentToLessonFragment(id,content)
                Log.d("LESSON_ADAPTER", id.toString())
                this@LessonsAdapter.fragment
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