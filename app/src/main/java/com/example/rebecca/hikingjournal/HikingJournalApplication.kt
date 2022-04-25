package com.example.rebecca.hikingjournal

import android.app.Application

class HikingJournalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        HikeRepository.initialize(this)
    }
}