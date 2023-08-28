package com.example.greenatomwidget

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import java.util.Date
import java.util.Objects

class ListDataProvider : ContentProvider()
{
    companion object {
        val CONTENT_URI : Uri = Uri.parse("content://com.example.greenatomwidget");
    }

    object Columns {
        const val ID: String = "_id";
        const val SHIP: String = "ship";
        const val PORT: String = "port";
        const val ARRIVAL_DATE: String = "arrival_date";
        const val ARRIVAL_LEFT: String = "arrival_left";
        const val DEPARTURE_DATE: String = "departure_date";
        const val DEPARTURE_LEFT: String = "departure_left";
    }

    val mData : ArrayList<ListDataModel> = ArrayList();

    override fun onCreate(): Boolean {
        mData.add(ListDataModel("Ship Test", "Port Test", Date(123123), 3, Date(123132), 15));

        return true;
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        if (Uri.EMPTY.equals(uri))
        {
            return null;
        }

        val cursor = MatrixCursor(
            arrayOf(
                Columns.ID,
                Columns.SHIP,
                Columns.PORT,
                Columns.ARRIVAL_DATE,
                Columns.ARRIVAL_LEFT,
                Columns.DEPARTURE_DATE,
                Columns.DEPARTURE_LEFT));

        mData.forEachIndexed { index, element ->
            cursor.addRow(arrayOf(
                index,
                element.mShip,
                element.mPort,
                element.mArrivalDate,
                element.mArrivalLeft,
                element.mDepartureDate,
                element.mDepartureLeft));
        }

        return cursor;
    }

    override fun getType(uri: Uri): String? {
        return "lol";
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri,
                        values: ContentValues?,
                        selection: String?,
                        selectionArgs: Array<out String>?): Int {
        if (uri.pathSegments.size != 1)
        {
            return 0;
        }
        val index: Int = Integer.parseInt(uri.pathSegments.get(0));
        if (index >= 0 && index < mData.size)
        {
            if (values != null) {
                mData[index].mShip = values.getAsString(Columns.SHIP)
                mData[index].mPort = values.getAsString(Columns.PORT);
                mData[index].mArrivalDate = Date(values.getAsLong(Columns.ARRIVAL_DATE));
                mData[index].mArrivalLeft = values.getAsInteger(Columns.ARRIVAL_LEFT);
                mData[index].mDepartureDate = Date(values.getAsLong(Columns.DEPARTURE_DATE));
                mData[index].mDepartureLeft = values.getAsInteger(Columns.DEPARTURE_LEFT);

                context?.contentResolver?.notifyChange(uri, null);

                return 1;
            }
        }
        return 0;
    }
}