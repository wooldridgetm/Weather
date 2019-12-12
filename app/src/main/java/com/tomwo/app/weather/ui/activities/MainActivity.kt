package com.tomwo.app.weather.ui.activities

import android.os.Bundle
import android.util.Log.i
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tomwo.app.weather.R
import com.tomwo.app.weather.R.id.forecastList
import com.tomwo.app.weather.domain.commands.RequestDayForecastCommand
import com.tomwo.app.weather.domain.commands.RequestForecastCommand
import com.tomwo.app.weather.domain.model.ForecastList
import com.tomwo.app.weather.extensions.DelegatesExt
import com.tomwo.app.weather.ui.adapters.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), ToolbarManager, AnkoLogger
{
    companion object {
        private const val TAG = "MainActivity"
    }
    private val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        attachToScroll( forecastList )
    }

    override fun onResume()
    {
        super.onResume()
        loadForecast()
    }

    /*private fun loadForecast(): Deferred<Unit> = async(Contacts.Intents.UI) {

        val zip = zipCode
        val result = bg {
            RequestForecastCommand(zip).execute()
        }
        updateUI( result.await() )
    }*/

    private fun loadForecast() = doAsync {
        val result = RequestForecastCommand(zipCode).execute()
        uiThread {
            updateUI(result)
        }
    }

    private fun updateUI(weekForecast: ForecastList)
    {
        forecastList.adapter = ForecastListAdapter(weekForecast) {
            startActivity<DetailActivity>(DetailActivity.ID to it.id, DetailActivity.CITY_NAME to weekForecast.city)
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        }
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
    }

    override fun setExitTransition() = overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)

    override fun dimStatusBar()
    {
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
    }

    override fun resetStatusBar()
    {
        this.window.decorView.systemUiVisibility = 0
    }
}
