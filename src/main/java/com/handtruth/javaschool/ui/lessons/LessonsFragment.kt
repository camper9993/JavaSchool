package com.handtruth.javaschool.ui.lessons

import androidx.lifecycle.ViewModelProviders
import com.handtruth.javaschool.ui.lessontests.LessonTestsAdapter
import com.handtruth.javaschool.ui.lessontests.LessonTestsViewModel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.model.Lesson
import com.handtruth.javaschool.data.model.ModuleTests

class LessonsFragment : Fragment() {

    companion object {
        fun newInstance() =
            LessonsFragment()
    }
    @Suppress("PrivatePropertyName")
    private val TAG = "TestsFragment"

    private var recycleview: RecyclerView? = null

    private lateinit var viewModel: LessonsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.lesson_tests_fragment, container, false)

        recycleview = rootView.findViewById(R.id.lesson_tests_list) as RecyclerView

        Log.e(TAG, "onCreateView called!")

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LessonsViewModel::class.java)
        viewModel.setApplication(application = activity!!.application)

        Log.e("KEK", "onActivityCreated called!")
        val adapter = LessonsAdapter(this ,viewModel)

        recycleview!!.layoutManager = LinearLayoutManager(this.context)
        recycleview!!.setHasFixedSize(true)
        recycleview!!.adapter = adapter


        viewModel.getAllTest().observe(this, Observer {
            adapter.setLessonTests(it as MutableList<Lesson>)
        })
    }

}
