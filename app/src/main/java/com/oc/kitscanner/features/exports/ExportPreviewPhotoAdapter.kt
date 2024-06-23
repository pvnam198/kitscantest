package com.oc.kitscanner.features.exports

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.oc.base.views.adapters.BaseAdapter
import com.oc.base.views.adapters.BaseViewHolder
import com.oc.base.views.adapters.OnSelectedItemListener
import com.oc.kitscanner.databinding.ItemExportPreviewPhotoBinding

class ExportPreviewPhotoAdapter : BaseAdapter<String, ItemExportPreviewPhotoBinding>() {

    override fun getViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<String, ItemExportPreviewPhotoBinding> {
        val binding = ItemExportPreviewPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val holder = object : BaseViewHolder<String, ItemExportPreviewPhotoBinding>(binding) {
            override fun onBindItem(model: String, binding: ItemExportPreviewPhotoBinding) {
                Glide.with(binding.root).load(model).into(binding.ivPhoto)
            }
        }
        holder.setListener(object : OnSelectedItemListener<String> {
            override fun onClick(item: String) {
                onSelectedItemListener?.onClick(item)
            }
        })
        return holder
    }

}