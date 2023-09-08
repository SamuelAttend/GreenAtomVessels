package com.example.greenatomvessels

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ALARM_ACTION = "com.example.android.greenatomvessels.ALARM_ACTION"
    }

    @SuppressLint("Range")
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action != ALARM_ACTION) {
            return
        }

        val notificationService = context?.let { NotificationService(it) }
        val cursor = context?.contentResolver?.query(DataProvider.DATA_URI, null, null, null, null, null)
        val date = SimpleDateFormat(DataProvider.DATE_FORMAT, Locale.getDefault()).format(Calendar.getInstance().time)

        while (cursor!!.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(DataProvider.Columns.FAVORITE)).toBoolean()) {
                var arrival : Boolean? = null
                when (date) {
                    cursor.getString(cursor.getColumnIndex(DataProvider.Columns.ARRIVAL_DATE)) -> {
                        arrival = true
                    }
                    cursor.getString(cursor.getColumnIndex(DataProvider.Columns.DEPARTURE_DATE)) -> {
                        arrival = false
                    }
                }

                if (arrival != null) {
                    notificationService?.showNotification(
                        cursor.getString(cursor.getColumnIndex(DataProvider.Columns.VESSEL)),
                        cursor.getString(cursor.getColumnIndex(DataProvider.Columns.PORT)),
                        arrival
                    )
                }
            }
        }

        notificationService?.lastID = 0
        cursor.close()
    }
}