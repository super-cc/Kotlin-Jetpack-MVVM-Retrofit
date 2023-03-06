package love.isuper.core.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import love.isuper.core.ext.inflateBindingWithGeneric
import love.isuper.core.base.BaseLogFragment
import java.lang.reflect.ParameterizedType

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseFragment
 */
abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : BaseLogFragment() {

    protected var _firstLoad = true
    private var _binding: VB? = null
    val mViewBinding: VB get() = _binding!!
    val mViewModel: VM by lazy { createViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = inflateBindingWithGeneric(inflater, container, false)
        return mViewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
    }

    private fun load() {
        if (!_firstLoad) {
            return
        }
        _firstLoad = false
        mViewModel.init(arguments)
        init()
        liveDataObserver()
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
                    ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
                    .get(tClass as Class<VM>)
        }
        throw RuntimeException("ViewModel init error")
    }

    /**
     * 初始化
     */
    protected abstract fun init()

    /**
     * LiveData的Observer
     */
    protected abstract fun liveDataObserver()

    /**
     * 返回TAG
     */
    protected fun TAG(): String {
        this::class.simpleName?.let {
            return it
        }
        return "BaseFragment"
    }

}