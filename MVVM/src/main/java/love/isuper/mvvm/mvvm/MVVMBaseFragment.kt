package love.isuper.mvvm.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import love.isuper.mvvm.base.BaseFragment
import java.lang.reflect.ParameterizedType

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseFragment
 */
abstract class MVVMBaseFragment<VM : BaseViewModel>(private val layoutId: Int) : BaseFragment() {

    val mViewModel: VM by lazy { createViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
    }

    private fun load() {
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
     * 返回ViewModelStoreOwner
     */
    protected open fun getViewModelStoreOwner() : ViewModelStoreOwner {
        return this
    }

    /**
     * 创建ViewModel
     */
    protected open fun createViewModel(): VM {
        //这里获得到的是类的泛型的类型
        val type = javaClass.genericSuperclass
        if (type != null && type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[0]
            return ViewModelProvider(getViewModelStoreOwner(),
                    ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
                    .get(tClass as Class<VM>)
        }
        throw MVVMRuntimeException("ViewModel init error")
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
        mViewModel.loadStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                LoadState.LoadStart -> loadStart()
                LoadState.LoadSuccess -> loadFinish(true)
                LoadState.LoadFail -> loadFinish(false)
            }
        }
        mViewModel.hasMoreStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                HasMoreState.HasMore -> hasMore()
                HasMoreState.NoMore -> noMore()
            }
        }
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