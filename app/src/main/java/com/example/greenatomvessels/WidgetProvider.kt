package com.example.greenatomvessels

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class WidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    )
    {
        appWidgetIds.forEach { appWidgetId ->
            val appPendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                 0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            val rv = RemoteViews(
                context.packageName,
                R.layout.widget_layout
            )

            rv.setOnClickPendingIntent(R.id.layout1, appPendingIntent)
            rv.setRemoteAdapter(R.id.listView, Intent(context, WidgetListViewService::class.java))

            val listViewIntent = Intent(context, ListItemReceiver::class.java)
            listViewIntent.action = ListItemReceiver.LIST_ITEM_ACTION
            val listViewPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                listViewIntent,
                PendingIntent.FLAG_MUTABLE)
            rv.setPendingIntentTemplate(R.id.listView, listViewPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, rv)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    companion object {
        fun notifyChange(context: Context) {
            val mgr = AppWidgetManager.getInstance(context)
            val cn = ComponentName(context, WidgetProvider::class.java)
            mgr.notifyAppWidgetViewDataChanged(
                mgr.getAppWidgetIds(cn), R.id.listView
            )
        }
    }
}
