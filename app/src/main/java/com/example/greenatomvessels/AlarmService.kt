package com.example.greenatomvessels

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class AlarmService(mContext: Context) {
    companion object {
        const val ALARM_NOTIFICATION_RC = 100
    }
    private var alarmMgr: AlarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var alarmPendingIntent: PendingIntent = PendingIntent.getBroadcast(
        mContext,
        ALARM_NOTIFICATION_RC,
        Intent(mContext, AlarmReceiver::class.java).apply { action = AlarmReceiver.ALARM_ACTION },
        PendingIntent.FLAG_IMMUTABLE
    )

    fun initRepeatingAlarm() {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmMgr.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )
    }
}