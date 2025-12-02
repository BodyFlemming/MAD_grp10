package com.example.mymons

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// FORMAT 1: "12/05/25 23:45"
fun Date.toNumericString(): String {
    val formatter = SimpleDateFormat("dd/MM/yy HH:mm", Locale.US)
    return formatter.format(this)
}

// FORMAT 2 (Preferred): "22. nov 2025"
fun Date.toPrettyString(): String {
    // "MMM" gives the short month name (Jan, Feb, Nov)
    // Locale.getDefault() ensures it is in the user's language (e.g., "okt" vs "Oct")
    val formatter = SimpleDateFormat("dd. MMM yyyy", Locale.US)
    return formatter.format(this)
}