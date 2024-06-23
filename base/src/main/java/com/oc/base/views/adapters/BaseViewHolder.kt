package com.oc.base.views.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * [M] Model
 * [V] ViewBinding
 * **/
abstract class BaseViewHolder<M, V : ViewBinding>(private val binding: V) :
    RecyclerView.ViewHolder(binding.root) {

    private var onSelectedItemListener: OnSelectedItemListener<M>? = null

    fun setListener(onSelectedItemListener: OnSelectedItemListener<M>) {
        this.onSelectedItemListener = onSelectedItemListener
    }

    fun bind(model: M) {
        binding.root.setOnClickListener {
            onSelectedItemListener?.onClick(model)
        }
        onBindItem(model, binding)
    }

    abstract fun onBindItem(model: M, binding: V)

}