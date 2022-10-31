package love.isuper.core.mvvm

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseModel
 */
abstract class BaseViewModel : ViewModel() {

    open fun init(arguments: Bundle?) {}

    /**
     * 回收时任务
     */
    override fun onCleared() {
        Log.v("BaseViewModel", this.javaClass.name + this + " onCleared()")
        super.onCleared()
    }

    /**
     * 返回TAG
     */
    protected fun TAG(): String {
        this::class.simpleName?.let {
            return it
        }
        return "BaseViewModel"
    }

}