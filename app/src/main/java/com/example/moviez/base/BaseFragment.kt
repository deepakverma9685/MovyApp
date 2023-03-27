package com.example.moviez.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.fragment.app.Fragment
import com.example.moviez.R
import com.example.moviez.listners.BaseView

abstract class BaseFragment<B : ViewDataBinding> : Fragment(), BaseView {



    @LayoutRes
    protected abstract fun getLayoutRes(): Int
    private var progressView: Dialog? = null
    protected var binding: B? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (binding?.root?.parent as? ViewGroup)?.removeView(binding?.root)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    abstract override fun initViews()

    abstract override fun observeViewModel()


    /**
     * Shows progress in an activity
     */
    fun showProgressDialog(message: String = "") {
        if (progressView == null) {
            progressView = context?.let { Dialog(it) };
            progressView?.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressView?.setContentView(R.layout.progress_layout);
            progressView?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            progressView?.setCancelable(false);
            progressView?.findViewById<TextView>(R.id.txtMessage)?.text = message
            progressView?.show()
        }
    }

    /**
     * Hides progress in an activity
     */
    fun hideProgressDialog() {
        if (progressView != null) {
            progressView?.dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        progressView = null
    }
}