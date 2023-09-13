package com.example.rawggames.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDateString(dateTimeString: String): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    val date = inputFormat.parse(dateTimeString)
    date?.let {
        return outputFormat.format(it)
    } ?: run {
        return ""
    }
}