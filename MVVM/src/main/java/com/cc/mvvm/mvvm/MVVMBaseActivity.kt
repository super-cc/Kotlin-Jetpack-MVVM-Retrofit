package com.cc.mvvm.mvvm

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.cc.mvvm.base.BaseActivity
import java.lang.reflect.ParameterizedType

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseActivity
 */
abstract class MVVMBaseActivity<V : ViewBinding, M : BaseViewModel> : BaseActivity() {

    protected lateinit var mViewBinding: V

    protected lateinit var mViewModel: M

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare()
        mViewBinding = getViewBinding()
        setContentView(mViewBinding.root)
        mViewModel = getViewModel()!!
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
     * 获取ViewBinding
     */
    abstract fun getViewBinding(): V

    /**
     * 返回ViewModelStoreOwner
     */
    protected open fun getViewModelStoreOwner() : ViewModelStoreOwner {
        return this
    }

    /**
     * 获取ViewModel
     */
    protected open fun getViewModel(): M? {
        //这里获得到的是类的泛型的类型
        val type = javaClass.genericSuperclass
        if (type != null && type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[1]
            return ViewModelProvider(this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                    .get(tClass as Class<M>)
        }
        return null
    }

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