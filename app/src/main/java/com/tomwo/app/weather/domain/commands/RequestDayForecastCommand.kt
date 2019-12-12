package com.tomwo.app.weather.domain.commands

import com.tomwo.app.weather.domain.datasource.ForecastProvider
import com.tomwo.app.weather.domain.model.Forecast

class RequestDayForecastCommand(val id: Long,
                                private val forecastProvider: ForecastProvider = ForecastProvider()) : Command<Forecast>
{

    override fun execute() = forecastProvider.requestForecast(id)
}