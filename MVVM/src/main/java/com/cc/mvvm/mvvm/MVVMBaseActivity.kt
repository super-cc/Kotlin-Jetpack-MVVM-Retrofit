package com.cc.mvvm.mvvm

import android.os.Bundle
import androidx.lifecycle.Observer
import com.cc.mvvm.base.BaseActivity

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseActivity
 */
abstract class MVVMBaseActivity<M : BaseViewModel> : BaseActivity() {

    protected abstract val layoutId: Int

    protected lateinit var mViewModel: M

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare()
        setContentView(layoutId)
        mViewModel = getViewModel()
        mViewModel.init(if (intent != null) intent.extras else null)
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
     * 回调刷新控件状态
     */
    private fun loadState() {
        mViewModel.loadStateLiveData.observe(this, Observer {
            when (it) {
                LoadState.LoadStart -> loadStart()
                LoadState.LoadSuccess -> loadFinish(true)
                LoadState.LoadFail -> loadFinish(false)
            }
        })
        mViewModel.hasMoreStateLiveData.observe(this, Observer {
            when (it) {
                HasMoreState.HasMore -> hasMore()
                HasMoreState.NoMore -> noMore()
            }
        })
    }

    /**
     * 初始化
     */
    protected abstract fun init()

    //加载开始
    protected open fun loadStart() {}

    //加载结束
    protected open fun loadFinish(success: Boolean) {}

    //有下一页
    protected open fun hasMore() {}

    //无下一页
    protected open fun noMore() {}

}