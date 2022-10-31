package love.isuper.core.mvvm

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import love.isuper.core.base.BaseLogActivity
import love.isuper.core.ext.inflateBindingWithGeneric
import java.lang.reflect.ParameterizedType

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseActivity
 */
abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : BaseLogActivity() {

    lateinit var mViewBinding: VB
    val mViewModel: VM by lazy { createViewModel() }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare()
        mViewBinding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mViewBinding.root)
        mViewModel.init(if (intent != null) intent.extras else null)
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
    protected open fun getViewModelStoreOwner(): ViewModelStoreOwner {
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
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                    .get(tClass as Class<VM>)
        }
        throw RuntimeException("ViewModel init error")
    }

    /**
     * LiveData的Observer
     */
    protected abstract fun liveDataObserver()

    /**
     * 初始化
     */
    protected abstract fun init()

    /**
     * 返回TAG
     */
    protected fun TAG(): String {
        this::class.simpleName?.let {
            return it
        }
        return "BaseActivity"
    }

}