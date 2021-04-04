package com.example.nasamarsrover.view.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun getRootLayoutId(): Int

    abstract fun initView(rootView: View)

    private var rootView: View? = null
    private var isFirstViewCreation = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == rootView) {
            isFirstViewCreation = true
            rootView = inflater.inflate(getRootLayoutId(), container, false)
        } else {
            isFirstViewCreation = false
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFirstViewCreation) {
            view.requestApplyInsets()
            initView(view)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView = null
    }
}