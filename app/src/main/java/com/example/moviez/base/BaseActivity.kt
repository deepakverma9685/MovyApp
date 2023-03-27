package com.example.moviez.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.moviez.listners.BaseView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), BaseView {

    protected lateinit var binding: B

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initViews()
        observeViewModel()
    }

    abstract override fun initViews()

    abstract override fun observeViewModel()

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}


