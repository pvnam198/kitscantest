package com.oc.base.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.greenrobot.eventbus.EventBus

abstract class BaseBottomSheetDialog<T : ViewBinding>: BottomSheetDialogFragment() {
    open val binding by lazy { bindingView() }

    open var registerEventBus = false

    open val useDim: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (registerEventBus) EventBus.getDefault().register(this)
        initObserver()
        initConfig(view, savedInstanceState)
        initListener()
        initTask()
    }

    override fun onStart() {
        super.onStart()
        if (!useDim) {
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        if (registerEventBus) EventBus.getDefault().unregister(this)
        release()
        super.onDestroyView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.findFragmentByTag(tag).let { fragment ->
            fragment ?: let {
                manager.beginTransaction().let { transition ->
                    this.show(transition, tag)
                }
            }
        }
    }

    open fun initObserver() {}

    open fun initConfig(view: View, savedInstanceState: Bundle?) {}

    open fun initListener() {}

    open fun initTask() {}

    open fun release() {}

    abstract fun bindingView(): T

}