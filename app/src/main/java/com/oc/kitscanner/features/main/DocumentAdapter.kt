package com.oc.kitscanner.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.oc.base.views.adapters.BaseAdapter
import com.oc.base.views.adapters.BaseViewHolder
import com.oc.converters.f05
import com.oc.converters.millisToTime
import com.oc.domain.models.Document
import com.oc.kitscanner.databinding.ItemDocumentBinding

class DocumentAdapter : BaseAdapter<Document, ItemDocumentBinding>() {

    override fun getViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Document, ItemDocumentBinding> {
        val binding =
            ItemDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val documentHolder = object : BaseViewHolder<Document, ItemDocumentBinding>(binding) {
            override fun onBindItem(model: Document, binding: ItemDocumentBinding) {
                binding.tvDocumentName.text = model.name
                binding.tvDocumentTime.text = millisToTime(model.timeMillis, f05)
                binding.tvCount.text = model.urls.size.toString()
                Glide.with(binding.root.context).load(model.urls[0])
                    .into(binding.ivDocumentImage)
            }
        }
        return documentHolder
    }

}