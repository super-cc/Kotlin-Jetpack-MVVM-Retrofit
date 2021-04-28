package com.cc.mvvm.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cc.mvvm.base.BaseFragment

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseFragment
 */
abstract class MVVMBaseFragment<M : BaseViewModel> : BaseFragment() {

    abstract val layoutId: Int

    private var mRootView: View? = null

    protected lateinit var mViewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(layoutId, container, false)

        return mRootView!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
    }

    private fun load() {
        mViewModel = getViewModel()
        mViewModel.init(arguments)
        loadState()
        onRegisterLiveListener()
        liveDataObserver()
        init()
    }

    /**
     * 预配置
     */
    protected open fun onPrepare() {}

    /**
     * 获取ViewModel
     */
    abstract fun getViewModel(): M

    /**
     * LiveEventBus的Listener
     */
    protected open fun onRegisterLiveListener() {}

    /**
     * LiveData的Observer
     */
    protected abstract fun liveDataObserver()

    /**
     * 初始化
     */
    protected abstract fun init()

    /**
     * 回调刷新控件状态
     */
    private fun loadState() {
        mViewModel.loadStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoadState.LoadStart -> loadStart()
                LoadState.LoadSuccess -> loadFinish(true)
                LoadState.LoadFail -> loadFinish(false)
            }
        })
        mViewModel.hasMoreStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                HasMoreState.HasMore -> hasMore()
                HasMoreState.NoMore -> noMore()
            }
        })
    }

    //加载开始
    protected open fun loadStart() {}

    //加载结束
    protected open fun loadFinish(success: Boolean) {}

    //有下一页
    protected open fun hasMore() {}

    //无下一页
    protected open fun noMore() {}

}