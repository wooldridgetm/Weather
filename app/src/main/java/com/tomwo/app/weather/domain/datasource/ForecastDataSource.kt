package com.tomwo.app.weather.domain.datasource

import com.tomwo.app.weather.domain.model.Forecast
import com.tomwo.app.weather.domain.model.ForecastList

interface ForecastDataSource
{
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}