package com.tomwo.app.weather.domain.commands

import com.tomwo.app.weather.domain.datasource.ForecastProvider
import com.tomwo.app.weather.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long,
                             private val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList>
{
    companion object {
        val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}