package com.oc.kitscanner.features.main.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.oc.base.views.BaseActivity
import com.oc.base.views.adapters.OnSelectedItemListener
import com.oc.domain.models.Document
import com.oc.domain.services.ScannerService
import com.oc.kitscanner.databinding.ActivityMainBinding
import com.oc.kitscanner.features.detail_edit.DetailEditActivity
import com.oc.kitscanner.features.main.DocumentAdapter
import com.oc.kitscanner.features.main.models.DocumentResult
import com.oc.kitscanner.features.main.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var scannerService: ScannerService

    private lateinit var documentAdapter: DocumentAdapter

    private val viewModel by viewModels<MainViewModel>()

    override fun bindingView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initConfig(savedInstanceState: Bundle?) {
        super.initConfig(savedInstanceState)
        initAdapter()
        getAllDocuments()
    }

    private fun getAllDocuments() {
        binding.swiperefresh.isRefreshing = true
        viewModel.getAllDocuments()
    }

    private fun initAdapter() {
        documentAdapter = DocumentAdapter()
        documentAdapter.setListener(object : OnSelectedItemListener<Document> {
            override fun onClick(item: Document) {
                startActivity(DetailEditActivity.getIntent(this@MainActivity, item))
            }
        })
        binding.rvDocuments.adapter = documentAdapter
    }

    override fun initListener() {
        super.initListener()
        binding.btnScanDoc.setOnClickListener {
            viewModel.scanAndSaveToDatabase(scannerService)
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.getAllDocuments()
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.documents.observe(this) {
            binding.swiperefresh.isRefreshing = false
            documentAdapter.set(it)
        }

        viewModel.documentResult.observe(this) {
            when (it) {
                is DocumentResult.Saving -> {
                    binding.llSavingDocuments.visibility = View.VISIBLE
                    binding.pbSavingDocuments.progress = 0
                }

                is DocumentResult.Success -> {
                    binding.llSavingDocuments.visibility = View.GONE
                }

                is DocumentResult.Failure -> {
                    binding.llSavingDocuments.visibility = View.GONE
                }

                is DocumentResult.Cancel -> {
                    binding.llSavingDocuments.visibility = View.GONE
                }
            }
        }

        viewModel.progress.observe(this) {
            binding.pbSavingDocuments.progress = it
        }

    }

}