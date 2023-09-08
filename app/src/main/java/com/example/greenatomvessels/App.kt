package com.example.greenatomvessels

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val notificationService = NotificationService(applicationContext)
        notificationService.createNotificationChannel()

        val alarmService = AlarmService(applicationContext)
        alarmService.initRepeatingAlarm()

        val dataObserver = DataObserver(applicationContext, null)
        dataObserver.observeOverContent(DataProvider.DATA_URI)
    }
}