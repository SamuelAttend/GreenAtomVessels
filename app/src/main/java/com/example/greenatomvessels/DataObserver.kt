package com.example.greenatomvessels

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

class DataObserver(context: Context, handler: Handler? = null) : ContentObserver(handler) { // НЕ ИСПОЛЬЗУЕТСЯ
    private var mContext = context

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        WidgetProvider.sendRefreshBroadcast(mContext)
    }

    fun startObservingContent(uri: Uri) {
        mContext.contentResolver?.registerContentObserver(uri, false, this)
    }

    fun stopObservingContent() {
        mContext.contentResolver.unregisterContentObserver(this)
    }
}