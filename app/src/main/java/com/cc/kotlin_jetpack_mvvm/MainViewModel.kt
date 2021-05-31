package com.cc.kotlin_jetpack_mvvm

import androidx.lifecycle.MutableLiveData
import com.cc.mvvm.mvvm.BaseViewModel

/**
 * Created by guoshichao on 2021/4/28
 */
class MainViewModel : BaseViewModel() {

    val modelLiveData = MutableLiveData<String>()

    fun getModel() {
        modelLiveData.value = "Hello!!!"
    }

}