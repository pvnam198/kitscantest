package com.oc.kitscanner.features.exports

import android.os.Bundle
import android.view.View
import com.oc.base.views.BaseBottomSheetDialog
import com.oc.kitscanner.databinding.BottomSheetDialogExportBinding

class ExportDialog : BaseBottomSheetDialog<BottomSheetDialogExportBinding>() {
    override fun bindingView(): BottomSheetDialogExportBinding {
        return BottomSheetDialogExportBinding.inflate(layoutInflater)
    }

    companion object {
        const val TAG = "ExportDialog"
        fun getInstance(): ExportDialog {
            return ExportDialog()
        }
    }

    override fun initConfig(view: View, savedInstanceState: Bundle?) {
        super.initConfig(view, savedInstanceState)
    }

}