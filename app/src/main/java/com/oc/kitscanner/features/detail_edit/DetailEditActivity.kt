package com.oc.kitscanner.features.detail_edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.oc.base.views.BaseActivity
import com.oc.domain.models.Document
import com.oc.kitscanner.databinding.ActivityDetailEditBinding
import com.oc.kitscanner.features.exports.ExportDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailEditActivity : BaseActivity<ActivityDetailEditBinding>() {
    override fun bindingView(): ActivityDetailEditBinding {
        return ActivityDetailEditBinding.inflate(layoutInflater)
    }

    companion object {
        const val KEY_DOCUMENT = "key_document"

        fun getIntent(context: Context, document: Document): Intent {
            val intent = Intent(context, DetailEditActivity::class.java)
            intent.putExtra(KEY_DOCUMENT, document)
            return intent
        }
    }

    private val viewModel by viewModels<DetailEditViewModel>()
    private lateinit var photoAdapter: PhotoAdapter

    override fun initConfig(savedInstanceState: Bundle?) {
        super.initConfig(savedInstanceState)
        binding.tvTitle.text = viewModel.document.name
        initPhotoAdapter()
    }

    override fun initListener() {
        super.initListener()
        binding.btnBack.setOnClickListener {
            backToPrevious()
        }
        binding.btnExport.setOnClickListener {
            ExportDialog.getInstance(viewModel.document).show(supportFragmentManager, ExportDialog.TAG)
        }
    }

    private fun initPhotoAdapter() {
        photoAdapter = PhotoAdapter()
        binding.rvPhoto.adapter = photoAdapter
        photoAdapter.setPhotos(viewModel.document.urls)
    }

}