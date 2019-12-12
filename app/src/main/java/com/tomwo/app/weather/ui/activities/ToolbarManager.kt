package com.tomwo.app.weather.ui.activities

import android.content.Intent
import android.util.Log
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.tomwo.app.weather.R
import com.tomwo.app.weather.extensions.ctx
import com.tomwo.app.weather.extensions.slideEnter
import com.tomwo.app.weather.extensions.slideExit
import com.tomwo.app.weather.ui.App
import org.jetbrains.anko.startActivity

interface ToolbarManager
{
    val toolbar: Toolbar

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar()
    {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(App.instance, SettingsActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    App.instance.startActivity(intent)
                    setExitTransition()
                    true
                }
                else -> false
            }
        }
    }

    fun setExitTransition()
    {
        Log.d("ToolbarManager", "subclasses need to override this")
    }

    fun enableHomeAsUp(up: () -> Unit)
    {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = with(DrawerArrowDrawable(toolbar.ctx)) {
        progress = 1f
        this
    }

    fun attachToScroll(recyclerView: RecyclerView)
    {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
            {
                if (dy > 0)
                {
                    toolbar.slideExit()
                    dimStatusBar()
                    return
                }
                toolbar.slideEnter()
                resetStatusBar()
            }
        })
    }

    fun dimStatusBar() {
        Log.d("ToolbarManager", "classes need to override this")
    }

    fun resetStatusBar()
    {
        Log.d("ToolbarManager", "classes need to override this")
    }
}