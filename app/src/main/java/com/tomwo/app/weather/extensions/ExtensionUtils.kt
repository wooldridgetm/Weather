package com.tomwo.app.weather.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String
{
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}

fun Long.toDateStr(dateFormat: DateFormat = SimpleDateFormat("EE, MMM d, yyyy", Locale.getDefault())): String
{
    return dateFormat.format(this)

}
