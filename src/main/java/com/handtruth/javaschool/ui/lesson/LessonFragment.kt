package com.handtruth.javaschool.ui.lesson

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.model.ModuleTests
import com.handtruth.javaschool.ui.ModuleActivity
import com.handtruth.javaschool.ui.lessons.LessonsViewModel
import com.handtruth.javaschool.ui.lessontests.LessonTestsViewModel
import ru.noties.markwon.Markwon

class LessonFragment : Fragment() {

    private lateinit var viewModel: LessonViewModel
    private lateinit var content: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lesson_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)
        viewModel.setApplication(application = activity!!.application)
        content = LessonFragmentArgs.fromBundle(arguments!!).content
        val text = root.findViewById<TextView>(R.id.textView4)
        val markwon = Markwon.create(activity!!.applicationContext)
        markwon.setMarkdown(text,content)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}