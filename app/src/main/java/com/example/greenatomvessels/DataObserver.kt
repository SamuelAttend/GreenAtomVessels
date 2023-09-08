package com.example.greenatomvessels

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

class DataObserver(context: Context, handler: Handler?) : ContentObserver(handler) {
    private var mContext = context

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        WidgetProvider.sendRefreshBroadcast(mContext)
    }

    fun observeOverContent(uri: Uri) {
        mContext.contentResolver?.registerContentObserver(uri, true, this)
    }
}