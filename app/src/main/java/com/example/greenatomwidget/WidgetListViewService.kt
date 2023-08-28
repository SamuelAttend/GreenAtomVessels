package com.example.greenatomwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.util.Log
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import android.widget.RemoteViewsService
import org.jetbrains.annotations.Contract

class WidgetListViewService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext, intent);
    }

    class StackRemoteViewsFactory(context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {
        private val mContext = context;
        private var mCursor : Cursor? = null;

        override fun onCreate()
        {
        }

        override fun onDataSetChanged() {
            mCursor?.close();
            mCursor = mContext.contentResolver.query(ListDataProvider.CONTENT_URI, null, null, null, null);
        }

        override fun onDestroy() {
            mCursor?.close();
        }

        override fun getCount(): Int {
            return mCursor?.count ?: 0;
        }

        override fun getViewAt(position: Int): RemoteViews? {
            if (position == AdapterView.INVALID_POSITION || mCursor == null || !mCursor!!.moveToPosition(position))
            {
                return null;
            }
            val rv = RemoteViews(mContext.packageName, R.layout.list_item_layout);
            rv.setTextViewText(R.id.textView1, mCursor!!.getString(1));

            return rv;
        }

        override fun getLoadingView(): RemoteViews? {
            return null;
        }

        override fun getViewTypeCount(): Int {
            return 1;
        }

        override fun getItemId(position: Int): Long {
            return position.toLong();
        }

        override fun hasStableIds(): Boolean {
            return true;
        }

    }
}