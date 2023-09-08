package com.example.greenatomvessels

import android.content.BroadcastReceiver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent

class ListItemReceiver : BroadcastReceiver() {
    companion object {
        const val LIST_ITEM_ACTION = "com.example.android.greenatomvessels.LIST_ITEM_ACTION"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != LIST_ITEM_ACTION) {
            return
        }
        val id = intent.getStringExtra(DataProvider.Columns.ID)?.toLong()
        if (id != null) {
            val uri = ContentUris.withAppendedId(DataProvider.DATA_URI, id)
            val cv = ContentValues(1)
            cv.put(DataProvider.Columns.FAVORITE, DataProvider.FAVORITE_SWITCH_SIGNAL)
            context?.contentResolver?.update(uri, cv, null, null)
        }
    }
}