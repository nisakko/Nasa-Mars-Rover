package com.example.nasamarsrover.view.fragment.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseBindingDialogFragment<T : ViewDataBinding> : DialogFragment() {

    abstract fun applyBinding(binding: T)

    open fun requestFullscreen() = true

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initView(savedInstanceState: Bundle?)

    private var binding: T? = null
    private var isFirstViewCreation = false

    override fun onResume() {
        super.onResume()
        if(requestFullscreen()){
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == binding) {
            isFirstViewCreation = true
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        } else {
            isFirstViewCreation = false
        }
        return binding?.apply {
            lifecycleOwner = this@BaseBindingDialogFragment
            applyBinding(this)
            executePendingBindings()
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isFirstViewCreation) {
            view.requestApplyInsets()
            initView(savedInstanceState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}