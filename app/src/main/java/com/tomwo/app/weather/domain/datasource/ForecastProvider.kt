package com.tomwo.app.weather.domain.datasource

import com.tomwo.app.weather.data.db.ForecastDb
import com.tomwo.app.weather.data.server.ForecastServer
import com.tomwo.app.weather.domain.model.Forecast
import com.tomwo.app.weather.domain.model.ForecastList
import com.tomwo.app.weather.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES)
{
    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())

        private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = this.requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = this.requestToSources { it.requestDayForecast(id) }


    /**
     * helper function
     */
    private fun <T : Any> requestToSources(function: (ForecastDataSource) -> T?): T = sources.firstResult { function(it) }
}