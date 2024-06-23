package com.oc.domain.services

import com.oc.domain.callbacks.OnScanListener

interface ScannerService {

    fun scan(ls: OnScanListener)

    fun cancel()

}