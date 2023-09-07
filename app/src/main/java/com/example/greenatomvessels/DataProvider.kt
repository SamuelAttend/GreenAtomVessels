package com.example.greenatomvessels

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import java.lang.IndexOutOfBoundsException

class DataProvider : ContentProvider() {
    companion object {
        val DATA_URI : Uri = Uri.parse("content://com.example.greenatomvessels/data")
        const val FAVORITE_SWITCH_SIGNAL = 1
        const val DATE_FORMAT: String = "dd.MM.yy"
    }

    object Columns {
        const val ID: String = "_id"
        const val VESSEL: String = "vessel"
        const val PORT: String = "port"
        const val ARRIVAL_DATE: String = "arrival_date"
        const val ARRIVAL_LEFT: String = "arrival_left"
        const val DEPARTURE_DATE: String = "departure_date"
        const val DEPARTURE_LEFT: String = "departure_left"
        const val FAVORITE: String = "favorite"
    }

    private val mData : ArrayList<ListDataModel> = ArrayList()

    override fun onCreate(): Boolean {
        mData.add(ListDataModel("Vessel Test", "Port Test", "06.09.23", "3", "07.09.23", "15", true))
        mData.add(ListDataModel("Vessel Test", "Port Test", "07.09.23", "3", "07.09.23", "15"))
        mData.add(ListDataModel("Vessel Test", "Port Test", "07.09.23", "3", "07.09.23", "15"))
        mData.add(ListDataModel("Vessel Test Extra Extra EXTRA", "Port Test", "07.09.23", "3", "06.09.23", "15", true))
        mData.add(ListDataModel("Vessel Test", "Port Test", "07.09.23", "3", "07.09.23", "15"))
        mData.add(ListDataModel("Vessel Test", "Port Test", "07.09.23", "3", "07.09.23", "15"))
        mData.add(ListDataModel("Vessel Test", "Port Test", "07.09.23", "3", "07.09.23", "15"))

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val cursor = MatrixCursor(
            arrayOf(
                Columns.ID,
                Columns.VESSEL,
                Columns.PORT,
                Columns.ARRIVAL_DATE,
                Columns.ARRIVAL_LEFT,
                Columns.DEPARTURE_DATE,
                Columns.DEPARTURE_LEFT,
                Columns.FAVORITE))

        mData.forEachIndexed { index, element ->
            cursor.addRow(arrayOf(
                index,
                element.mVessel,
                element.mPort,
                element.mArrivalDate,
                element.mArrivalLeft,
                element.mDepartureDate,
                element.mDepartureLeft,
                element.mFavorite))
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values != null) {
            if (mData.add(ListDataModel(
                    values.getAsString(Columns.VESSEL),
                    values.getAsString(Columns.PORT),
                    values.getAsString(Columns.ARRIVAL_DATE),
                    values.getAsString(Columns.ARRIVAL_LEFT),
                    values.getAsString(Columns.DEPARTURE_DATE),
                    values.getAsString(Columns.DEPARTURE_LEFT)
                ))) {
                    context?.contentResolver?.notifyChange(uri, null)
                    val id: Long = mData.lastIndex.toLong()
                    return ContentUris.withAppendedId(DATA_URI, id)
                }
        }
        return null
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        val id: Int = Integer.parseInt(uri.pathSegments[1])
        try {
            mData.removeAt(id)
        } catch (e: IndexOutOfBoundsException) {
            return 0
        }
        context?.contentResolver?.notifyChange(uri, null)
        return 1
    }

    override fun update(uri: Uri,
                        values: ContentValues?,
                        selection: String?,
                        selectionArgs: Array<out String>?): Int {
        val id: Int = Integer.parseInt(uri.pathSegments[1])

        if (values != null) {
            if (values.getAsString(Columns.VESSEL) != null) mData[id].mVessel = values.getAsString(Columns.VESSEL)
            if (values.getAsString(Columns.PORT) != null) mData[id].mPort = values.getAsString(Columns.PORT)
            if (values.getAsString(Columns.ARRIVAL_DATE) != null) mData[id].mArrivalDate = values.getAsString(Columns.ARRIVAL_DATE)
            if (values.getAsString(Columns.ARRIVAL_LEFT) != null) mData[id].mArrivalLeft = values.getAsString(Columns.ARRIVAL_LEFT)
            if (values.getAsString(Columns.DEPARTURE_DATE) != null) mData[id].mDepartureDate = values.getAsString(Columns.DEPARTURE_DATE)
            if (values.getAsString(Columns.DEPARTURE_LEFT) != null) mData[id].mDepartureLeft = values.getAsString(Columns.DEPARTURE_LEFT)
            if (values.getAsString(Columns.FAVORITE) != null) mData[id].mFavorite = mData[id].mFavorite.not()

            context?.contentResolver?.notifyChange(uri, null)
            return 1
        }

        return 0
    }
}