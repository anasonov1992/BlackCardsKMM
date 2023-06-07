package com.example.blackcardskmm.android

import android.app.Application
import com.example.blackcardskmm.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class BlackCardsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(enableNetworkLogs = true) {
            androidLogger()
            androidContext(this@BlackCardsApplication)
        }
    }
}