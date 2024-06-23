package com.oc.kitscanner.features.exports

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.oc.base.views.BaseBottomSheetDialog
import com.oc.base.views.adapters.OnSelectedItemListener
import com.oc.domain.models.Document
import com.oc.kitscanner.databinding.BottomSheetDialogExportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExportDialog : BaseBottomSheetDialog<BottomSheetDialogExportBinding>() {
    override fun bindingView(): BottomSheetDialogExportBinding {
        return BottomSheetDialogExportBinding.inflate(layoutInflater)
    }

    companion object {

        const val TAG = "ExportDialog"

        const val KEY_DOCUMENT = "key_document"

        fun getInstance(document: Document): ExportDialog {
            val exportDialog = ExportDialog()
            val bundle = Bundle()
            bundle.putParcelable(KEY_DOCUMENT, document)
            exportDialog.arguments = bundle
            return exportDialog
        }
    }

    private val viewModel by viewModels<ExportDialogViewModel>()

    private lateinit var exportPreviewPhotoAdapter: ExportPreviewPhotoAdapter

    override fun initConfig(view: View, savedInstanceState: Bundle?) {
        super.initConfig(view, savedInstanceState)
        exportPreviewPhotoAdapter = ExportPreviewPhotoAdapter()
        exportPreviewPhotoAdapter.setListener(object : OnSelectedItemListener<String> {
            override fun onClick(item: String) {

            }
        })
        binding.rvPhotos.adapter = exportPreviewPhotoAdapter
        exportPreviewPhotoAdapter.set(viewModel.document.urls)
    }

    override fun initListener() {
        super.initListener()

        binding.btnExportAsPDF.setOnClickListener {

        }

        binding.btnExportAsJPG.setOnClickListener {

        }
    }

}