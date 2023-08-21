package com.example.greenatomwidget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import android.widget.RemoteViewsService

class WidgetListViewService : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return WidgetListView(this.applicationContext);
    }

    class WidgetListView(context: Context, data : ArrayList<WidgetDataModel>) : RemoteViewsService.RemoteViewsFactory {
        val mContext = context;
        var mData = data;


        override fun onCreate() {
            TODO("Not yet implemented")
        }

        override fun onDataSetChanged() {
            TODO("Not yet implemented")
        }

        override fun onDestroy() {
            TODO("Not yet implemented")
        }

        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getViewAt(position: Int): RemoteViews {
            val views : RemoteViews = RemoteViews(mContext.packageName, R.layout.list_item_layout);

            views.setTextViewText(R.id.)
        }

        override fun getLoadingView(): RemoteViews {
            TODO("Not yet implemented")
        }

        override fun getViewTypeCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getItemId(p0: Int): Long {
            TODO("Not yet implemented")
        }

        override fun hasStableIds(): Boolean {
            TODO("Not yet implemented")
        }

    }
}