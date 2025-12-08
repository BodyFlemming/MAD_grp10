package com.example.mymons

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// format: "12/05/25 23:45"
fun Date.toNumericString(): String {
    val formatter = SimpleDateFormat("dd/MM/yy HH:mm", Locale.US)
    return formatter.format(this)
}

// format "22. nov 2025"
fun Date.toPrettyString(): String {
    val formatter = SimpleDateFormat("dd. MMM yyyy", Locale.US)
    return formatter.format(this)
}