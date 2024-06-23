package com.oc.base.views.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * [M] Model
 * [V] ViewBinding
 * **/
abstract class BaseAdapter<M, V : ViewBinding> : RecyclerView.Adapter<BaseViewHolder<M, V>>() {

    private val _listData = ArrayList<M>()

    fun getListData(): List<M> = _listData

    private var _onSelectedItemListener: OnSelectedItemListener<M>? = null

    open fun setListener(onSelectedItemListener: OnSelectedItemListener<M>) {
        this._onSelectedItemListener = onSelectedItemListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun set(data: List<M>) {
        this._listData.clear()
        this._listData.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data: M): Boolean {
        try {
            val success = this._listData.add(data)
            if (success) notifyItemInserted(_listData.size - 1)
            return success
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun add(index: Int, data: M) {
        try {
            this._listData.add(index, data)
            notifyItemInserted(index)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun remove(data: M): Boolean {
        try {
            val index = this._listData.indexOf(data)
            val success = this._listData.remove(data)
            if (success) notifyItemRemoved(index)
            return success
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun remove(index: Int) {
        try {
            this._listData.removeAt(index)
            notifyItemRemoved(index)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        this._listData.clear()
        notifyDataSetChanged()
    }

    fun get(index: Int): M? {
        try {
            return this._listData[index]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<M, V> {
        val holder = getViewHolder(parent, viewType)
        holder.setListener(object : OnSelectedItemListener<M> {
            override fun onClick(item: M) {
                _onSelectedItemListener?.onClick(item)
            }
        })
        return holder
    }

    override fun getItemCount(): Int {
        return _listData.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<M, V>, position: Int) {
        holder.bind(_listData[position])
    }

    abstract fun getViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<M, V>

}