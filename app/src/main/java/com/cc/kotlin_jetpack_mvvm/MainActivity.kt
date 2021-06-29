package com.cc.kotlin_jetpack_mvvm

import android.view.View
import android.widget.Toast
import com.cc.kotlin_jetpack_mvvm.databinding.ActivityMainBinding
import com.cc.mvvm.mvvm.MVVMBaseActivity
import com.cc.mvvm.mvvm.observe

class MainActivity : MVVMBaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId: Int = R.layout.activity_main

    override fun bindViewBinding(view: View): ActivityMainBinding {
        return ActivityMainBinding.bind(view)
    }

    override fun liveDataObserver() {
        observe(mViewModel.modelLiveData, ::showToast)
    }

    private fun showToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun init() {
        mViewBinding.tvHello.setOnClickListener{
            mViewModel.getModel()
        }
    }

}