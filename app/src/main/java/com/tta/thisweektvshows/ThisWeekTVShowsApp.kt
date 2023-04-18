package com.tta.thisweektvshows

import android.app.Application
import com.tta.thisweektvshows.util.SharedPref

class ThisWeekTVShowsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
    }
}