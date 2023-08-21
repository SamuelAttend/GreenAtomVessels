package com.example.greenatomwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.RemoteViews

class WidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    )
    {
        appWidgetIds.forEach { appWidgetId ->
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                 0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            );

            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.widget_layout
            );

            views.setOnClickPendingIntent(R.id.layout1, pendingIntent);
            views.setRemoteAdapter(R.id.listView1, Intent(context, MainActivity::class.java));

//            val data = arrayOf("lol", "lol", "lol");
//            val adapter = ArrayAdapter(context, R.layout.widget_layout, data);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
