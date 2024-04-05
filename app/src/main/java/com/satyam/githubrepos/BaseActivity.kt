package com.satyam.githubrepos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity() {

    lateinit var viewDataBinding: T
    lateinit var mViewModel: V

    @LayoutRes
    abstract fun setLayoutId(): Int

    abstract fun setViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        performDataBinding()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, setLayoutId())
        mViewModel = setViewModel()
        viewDataBinding.executePendingBindings()
    }
}
