package com.oc.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Example: 22/06/2024
 **/
const val f01 = "dd/MM/yyyy"

/**
 * Example: 22-06-2024
 **/
const val f02 = "dd-MM-yyyy"

/**
 * Example: 22/06/2024 17:00:00
 **/
const val f03 = "dd/MM/yyyy HH:mm:ss"

/**
 * Example: 22-06-2024 17:00:00
 **/
const val f04 = "dd-MM-yyyy HH:mm:ss"

/**
 * Example: Jun 22, 2024
 **/
const val f05 = "MMM dd, yyyy"

fun millisToTime(millis: Long, format: String): String {
    val date = Date(millis)
    val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    return dateFormat.format(date)
}