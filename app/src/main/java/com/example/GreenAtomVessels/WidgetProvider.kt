package com.example.GreenAtomVessels

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
            val openAppPendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                 0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            );

            val views = RemoteViews(
                context.packageName,
                R.layout.widget_layout
            );

            views.setOnClickPendingIntent(R.id.layout1, openAppPendingIntent);
            views.setRemoteAdapter(R.id.listView1, Intent(context, WidgetListViewService::class.java));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    fun sendRefreshBroadcast(context: Context?) {
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.component = context?.let { ComponentName(it, WidgetProvider::class.java) };
        context?.sendBroadcast(intent);
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action;
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            val mgr = AppWidgetManager.getInstance(context);
            val cn = context?.let { ComponentName(it, WidgetProvider::class.java) };
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.listView1);
        }
        super.onReceive(context, intent)
    }
}
