package com.oc.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val mainScope get() = CoroutineScope(Dispatchers.Main)
val ioScope get() = CoroutineScope(Dispatchers.IO)
