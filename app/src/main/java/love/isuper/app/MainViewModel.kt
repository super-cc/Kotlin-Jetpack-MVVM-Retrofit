package love.isuper.app

import androidx.lifecycle.MutableLiveData
import love.isuper.mvvm.mvvm.BaseViewModel

/**
 * Created by guoshichao on 2021/4/28
 */
class MainViewModel : BaseViewModel() {

    val modelLiveData = MutableLiveData<String>()

    fun getModel() {
        modelLiveData.value = "Hello!!!"
    }

}