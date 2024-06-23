package com.oc.kitscanner.features.detail_edit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oc.kitscanner.databinding.ItemPhotoBinding

class PhotoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val photos = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPhotos(photos: List<String>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoHolder) {
            holder.bind(photos[position])
        }
    }

    inner class PhotoHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { }
        }

        fun bind(photo: String) {
            Glide.with(binding.root.context).load(photo).into(binding.ivPhoto)
            val strPos = (photos.indexOf(photo) + 1).toString()
            binding.tvPosition.text = strPos
        }

    }

}