package com.handtruth.javaschool.ui.modules

import android.os.Bundle
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

class ModulesFragment : Fragment() {
    companion object {
        fun newInstance() = ModulesFragment()
    }

    private lateinit var recycleview: RecyclerView
    private lateinit var viewModel: ModulesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_modules, container, false)

        recycleview = rootView.findViewById(R.id.store_list) as RecyclerView


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ModulesViewModel::class.java)
        viewModel.application = activity!!.application
        viewModel.setApplication()
        val adapter = ModulesAdapter(this.activity!!, viewModel)

        recycleview.layoutManager = LinearLayoutManager(this.context)
        recycleview.setHasFixedSize(true)
        recycleview.adapter = adapter

        viewModel.getAllModuleInfo().observe(this, Observer {
            adapter.setModuleInfo(it as MutableList<DownloadedModules>)
        })

    }

}