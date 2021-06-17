package com.cc.mvvm.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.cc.mvvm.base.BaseFragment
import java.lang.reflect.ParameterizedType

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseFragment
 */
abstract class MVVMBaseFragment<V : ViewBinding, M : BaseViewModel> : BaseFragment() {

    private var mViewBinding: V? = null

    private var mViewModel: M? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mViewBinding = createViewBinding()

        return getViewBinding().root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Fragment使用，需要在onDestroyView里将ViewBinding置空
        mViewBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
    }

    private fun load() {
        mViewModel = createViewModel()
        getViewModel().init(arguments)
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
     * 创建ViewBinding
     */
    abstract fun createViewBinding(): V

    /**
     * 获取ViewBinding
     */
    fun getViewBinding():V {
        if (mViewBinding != null) {
            return mViewBinding as V
        }
        throw MVVMRuntimeException("ViewBinding is null")
    }

    /**
     * 返回ViewModelStoreOwner
     */
    protected open fun getViewModelStoreOwner() : ViewModelStoreOwner {
        return this
    }

    /**
     * 创建ViewModel
     */
    protected open fun createViewModel(): M {
        //这里获得到的是类的泛型的类型
        val type = javaClass.genericSuperclass
        if (type != null && type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[1]
            return ViewModelProvider(this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
                    .get(tClass as Class<M>)
        }
        throw MVVMRuntimeException("ViewModel init error")
    }

    /**
     * 获取ViewModel
     */
    fun getViewModel(): M {
        if (mViewModel != null) {
            return mViewModel as M
        }
        throw MVVMRuntimeException("ViewModel is null")
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
     * 初始化
     */
    protected abstract fun init()

    /**
     * 回调刷新控件状态
     */
    private fun loadState() {
        getViewModel().loadStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoadState.LoadStart -> loadStart()
                LoadState.LoadSuccess -> loadFinish(true)
                LoadState.LoadFail -> loadFinish(false)
            }
        })
        getViewModel().hasMoreStateLiveData.observe(viewLifecycleOwner, Observer {
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