package com.example.greenatomvessels

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val notificationService = NotificationService(applicationContext)
        notificationService.initNotificationChannel()

        val alarmService = AlarmService(applicationContext)
        alarmService.initRepeatingAlarm()
    }
}