package com.tomwo.app.weather.data.server

import android.util.Log
import com.google.gson.Gson
import java.net.URL

class ForecastByZipCodeRequest(private val zipCode: Long, val gson: Gson = Gson())
{
    companion object
    {
        private const val APP_ID = "f52652e21c69fafad08e6d69e93af3e7"
        private const val URL = "https://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=imperial&cnt=14"
        private const val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }

    fun execute(): ForecastResult
    {
        val urlStr = "$COMPLETE_URL$zipCode,us"
        Log.d("Network", urlStr)
        val forecastJsonStr = URL(urlStr).readText()
        return gson.fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}