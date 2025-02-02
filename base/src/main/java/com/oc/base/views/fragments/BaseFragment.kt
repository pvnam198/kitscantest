package com.oc.base.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.oc.base.views.BaseActivity
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment<T : ViewBinding> : Fragment(), OnBackPressedListener {

    open val binding by lazy { bindingView() }

    open val registerEventBus = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (registerEventBus) EventBus.getDefault().register(this)
        initConfig(view, savedInstanceState)
        initObserver()
        initListener()
        initTask()
    }

    override fun onDestroyView() {
        if (registerEventBus) EventBus.getDefault().unregister(this)
        release()
        super.onDestroyView()
    }

    open fun initObserver() {}

    open fun initConfig(view: View, savedInstanceState: Bundle?) {}

    open fun initListener() {}

    open fun initTask() {}

    open fun release() {}

    abstract fun bindingView(): T

    private fun getBaseActivity() = activity as BaseActivity<*>

    override fun onBackPressed() {
        val fragments = childFragmentManager.fragments
        if (fragments.isEmpty()) {
            getBaseActivity().backToPrevious()
        } else {
            val fragment = fragments.last()
            if (fragment is OnBackPressedListener) {
                fragment.onBackPressed()
            }
        }
    }
}