package com.cc.mvvm.mvvm

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by guoshichao on 2021/2/20
 * MVVM BaseModel
 */
abstract class BaseViewModel : ViewModel() {

    val loadStateLiveData = MutableLiveData<LoadState>()
    val hasMoreStateLiveData = MutableLiveData<HasMoreState>()

    fun init(arguments: Bundle?) {}

    /**
     * 加载数据开始  用调用V层的loadStart
     */
    protected fun loadStart() {
        loadStateLiveData.value = LoadState.LoadStart
    }

    /**
     * 加载数据结束  用调用V层的loadFinish
     */
    protected fun loadFinish(success: Boolean) {
        if (success) {
            loadStateLiveData.setValue(LoadState.LoadSuccess)
        } else {
            loadStateLiveData.setValue(LoadState.LoadFail)
        }
    }

    /**
     * 是否有更多数据  用调用V层的HasMore和noMore
     */
    protected fun hasMore(hasMore : Boolean){
        if (hasMore) {
            hasMoreStateLiveData.value = HasMoreState.HasMore
        } else {
            hasMoreStateLiveData.value = HasMoreState.NoMore
        }
    }

    override fun onCleared() {
        Log.v("BaseViewModel", this.javaClass.name + this + " onCleared()")
        super.onCleared()
    }

}