package com.oc.maths

fun calculatePercent(totalItems: Int, currentIndex: Int, progress: Int): Int {
    val percentPerItem = 100f / totalItems
    return ((progress / 100f * percentPerItem) + (currentIndex * percentPerItem)).toInt()
}