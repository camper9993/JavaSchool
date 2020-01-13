package com.handtruth.javaschool.ui.simpletest

import androidx.lifecycle.ViewModelProviders
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
import com.handtruth.javaschool.data.model.Test

class SimpleTestFragment : Fragment() {

    companion object {
        fun newInstance() = SimpleTestFragment()
    }

    @Suppress("PrivatePropertyName")
    private val TAG = "SimpleTestFragment"

    private var recycleview: RecyclerView? = null
    private lateinit var viewModel: SimpleTestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.simple_test_fragment, container, false)

        recycleview = rootView.findViewById(R.id.tests_list) as RecyclerView

        Log.e(TAG, "onCreateView called!")

        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SimpleTestViewModel::class.java)
        val id = SimpleTestFragmentArgs.fromBundle(arguments!!).id
        viewModel.setApplication(activity!!.application, id)

        val adapter = SimpleTestAdapter(this.activity!!, viewModel)

        recycleview!!.layoutManager = LinearLayoutManager(this.context)
        recycleview!!.setHasFixedSize(true)
        recycleview!!.adapter = adapter

        viewModel.getAllTest().observe(this, Observer {
            adapter.setTest(it as MutableList<Test>)
        })
    }

}
