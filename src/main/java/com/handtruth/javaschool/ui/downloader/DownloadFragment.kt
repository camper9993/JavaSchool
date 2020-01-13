package com.handtruth.javaschool.ui.downloader

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.model.ModuleInfo
import android.widget.Toast
import com.handtruth.javaschool.connect.RepositoryProvider
import com.handtruth.javaschool.data.model.ModelViewReceiver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DownloadFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    @Suppress("PrivatePropertyName")
    private val TAG = "DownloadFragment"

    companion object {
        fun newInstance() = DownloadFragment()
    }

    private var recycleview: RecyclerView? = null
    lateinit var viewModel: DownloadViewModel
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.download_fragment, container, false)

        recycleview = rootView.findViewById(R.id.store_list) as RecyclerView

        mSwipeRefreshLayout = rootView.findViewById(R.id.refresh)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        // делаем повеселее
        mSwipeRefreshLayout.setColorSchemeColors(255, 234, 233)

        Log.e(TAG, "onCreateView called!")

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DownloadViewModel::class.java)
        viewModel.setApplication(activity!!.application)
        val adapter = DownloadAdapter(this.activity!!, viewModel)

        recycleview!!.layoutManager = LinearLayoutManager(this.context)
        recycleview!!.setHasFixedSize(true)
        recycleview!!.adapter = adapter

        viewModel.getAllModuleInfo().observe(this, Observer {
            adapter.setModuleInfo(it as MutableList<ModuleInfo>)
        })


    }

    override fun onRefresh() {
        // говорим о том, что собираемся начать
        Toast.makeText(this.context, "Старт обновления", Toast.LENGTH_SHORT).show()
        // начинаем показывать прогресс
        mSwipeRefreshLayout.isRefreshing = true
        // ждем 3 секунды и прячем прогресс
        mSwipeRefreshLayout.postDelayed({
            // говорим о том, что собираемся закончить
            RepositoryProvider.provideFilesRepository().getModules()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    viewModel.insertOrUpdate(result)
                    Log.d("Result", "There are ${result.size} Java developers in Lagos")
                }, { error ->
                    error.printStackTrace()
                })
            mSwipeRefreshLayout.isRefreshing = false
            Toast.makeText(this.context, "Обновление окончено", Toast.LENGTH_SHORT).show()
        }, 3000)
    }
}
