package com.handtruth.javaschool.ui.tests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.data.model.ModelViewReceiver
import com.handtruth.javaschool.data.model.ModuleInfo

class TestsFragment : Fragment() {
    companion object {
        fun newInstance() = TestsFragment()
    }

    @Suppress("PrivatePropertyName")
    private val TAG = "TestsFragment"

    private var recycleview: RecyclerView? = null
    lateinit var viewModel: TestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_tests, container, false)

        recycleview = rootView.findViewById(R.id.module_tests_list) as RecyclerView

        Log.e(TAG, "onCreateView called!")

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TestsViewModel::class.java)
        viewModel.setApplication(activity!!.application)
        ModelViewReceiver.testsViewModel = viewModel

        val adapter = TestsAdapter(activity!!, viewModel)

        recycleview!!.layoutManager = LinearLayoutManager(this.context)
        recycleview!!.setHasFixedSize(true)
        recycleview!!.adapter = adapter


        viewModel.getAllModuleInfo().observe(this, Observer {
            adapter.setTestInfo(it as MutableList<DownloadedModules>)
        })

    }
}