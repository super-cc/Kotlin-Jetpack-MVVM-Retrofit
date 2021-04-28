package com.cc.kotlin_jetpack_mvvm

import androidx.lifecycle.ViewModelProvider
import com.cc.mvvm.mvvm.MVVMBaseActivity

class MainActivity : MVVMBaseActivity<MainViewModel>() {

    override val layoutId: Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel {
        return ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(MainViewModel::class.java)
    }

    override fun liveDataObserver() {

    }

    override fun init() {

    }

}