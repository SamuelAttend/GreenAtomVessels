package com.example.greenatomvessels

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat


class NotificationService (private val mContext: Context) {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "vessels_channel"
    }

    var lastID = 0
    private val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Избранные рейсы",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Уведомляет о текущих избранных рейсах"

        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(vessel : String, port: String, arrival: Boolean) {
        val notification = NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
            .setContentText(if (arrival) "Судно \"$vessel\" прибывает сегодня в порт \"$port\"." else "Судно \"$vessel\" отправляется сегодня из порта \"$port\".")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        notificationManager.notify(
            lastID++,
            notification
        )
    }
}