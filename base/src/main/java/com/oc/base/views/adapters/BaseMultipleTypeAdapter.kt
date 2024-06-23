package com.oc.base.views.adapters

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class AdapterTypeConfig {
    protected abstract val typeHolderMapper: Map<Int, BaseMultipleTypeViewHolder>

    fun getViewHolder(viewType: Int): BaseMultipleTypeViewHolder {
        val holder = typeHolderMapper[viewType]
            ?: throw IllegalArgumentException("No ViewHolder found for viewType: $viewType")
        return holder
    }

    abstract fun getItemViewType(currentItem: Any): Int
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
        return adapterTypeConfig.getItemViewType(getListData()[position])
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