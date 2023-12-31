package com.example.greenatomvessels

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Binder
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class WidgetListViewService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }

    class StackRemoteViewsFactory(context: Context) : RemoteViewsFactory {
        private val mContext = context
        private var mCursor : Cursor? = null

        override fun onCreate()
        {
        }

        override fun onDataSetChanged() {
            val identityToken: Long = Binder.clearCallingIdentity()

            mCursor?.close()
            mCursor = mContext.contentResolver.query(DataProvider.DATA_URI, null, null, null, null)

            Binder.restoreCallingIdentity(identityToken)
        }

        override fun onDestroy() {
            mCursor?.close()
        }

        override fun getCount(): Int {
            return mCursor?.count ?: 0
        }

        @SuppressLint("Range")
        override fun getViewAt(position: Int): RemoteViews? {
            if (position == AdapterView.INVALID_POSITION || !mCursor!!.moveToPosition(position))
            {
                return null
            }
            val rv = RemoteViews(mContext.packageName, R.layout.list_item_layout)

            rv.setTextViewText(R.id.textViewVessel, mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.VESSEL)))
            rv.setTextViewText(R.id.textViewPort, mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.PORT)))
            rv.setTextViewText(R.id.textViewArrivalDate, mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.ARRIVAL_DATE)))
            rv.setTextViewText(R.id.textViewArrivalLeft, mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.ARRIVAL_LEFT)))
            rv.setTextViewText(R.id.textViewDepartureDate, mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.DEPARTURE_DATE)))
            rv.setTextViewText(R.id.textViewDepartureLeft, mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.DEPARTURE_LEFT)))

            rv.setInt(R.id.listItem, "setBackgroundColor",
                if (mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.FAVORITE)).toBoolean())
                    Color.parseColor("#C2FD8E")
                else
                    Color.parseColor("#FFFFFF"))

            val listItemIntent = Intent()
            listItemIntent.putExtra(DataProvider.Columns.ID, mCursor!!.getString(mCursor!!.getColumnIndex(DataProvider.Columns.ID)))
            rv.setOnClickFillInIntent(R.id.listItem, listItemIntent)

            return rv
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

    }
}