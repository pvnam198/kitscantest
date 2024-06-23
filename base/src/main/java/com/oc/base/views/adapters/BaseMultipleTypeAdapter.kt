package com.oc.base.views.adapters

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class AdapterTypeConfig {
    protected abstract val holderMapper: Map<Int, BaseMultipleTypeViewHolder>
    protected abstract val typeMapper: Map<Class<*>, Int>

    fun getViewHolder(viewType: Int): BaseMultipleTypeViewHolder {
        val holder = holderMapper[viewType]
            ?: throw IllegalArgumentException("No ViewHolder found for viewType: $viewType")
        return holder
    }

    fun getType(className: Class<*>): Int {
        val type = typeMapper[className]
            ?: throw IllegalArgumentException("No viewType found for class: $className")
        return type
    }
}

abstract class BaseMultipleTypeViewHolder(binding: ViewBinding) :
    BaseViewHolder<Any, ViewBinding>(binding)

abstract class BaseMultipleTypeAdapter : BaseAdapter<Any, ViewBinding>() {

    private lateinit var adapterTypeConfig: AdapterTypeConfig

    private var _onSelectedItemListener: OnSelectedItemListener<Any>? = null

    override fun setListener(onSelectedItemListener: OnSelectedItemListener<Any>) {
        this._onSelectedItemListener = onSelectedItemListener
    }

    abstract fun getAdapterTypeConfig(parent: ViewGroup, viewType: Int): AdapterTypeConfig

    override fun getItemViewType(position: Int): Int {
        return adapterTypeConfig.getType(getListData()[position]::class.java)
    }

    override fun getViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Any, ViewBinding> {
        adapterTypeConfig = getAdapterTypeConfig(parent, viewType)
        val holder = adapterTypeConfig.getViewHolder(viewType)
        holder.setListener(object : OnSelectedItemListener<Any> {
            override fun onClick(item: Any) {

            }
        })
        return holder
    }

}