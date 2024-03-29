package com.tomwo.app.weather.ui

import android.app.Application
import com.tomwo.app.weather.extensions.DelegatesExt

class App : Application()
{
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}