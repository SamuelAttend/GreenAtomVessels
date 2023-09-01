package com.example.GreenAtomVessels

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import java.util.Date

class DataProvider : ContentProvider()
{
    companion object {
        private val BASE_URI: Uri = Uri.parse("content://com.example.GreenAtomVessels");
        val LIST_DATA_URI : Uri = Uri.withAppendedPath(BASE_URI, "list");
        val FAVORITES_DATA_URI : Uri = Uri.withAppendedPath(BASE_URI, "favorites");
    }

    object Columns {
        const val ID: String = "_id";
        const val VESSEL: String = "ship";
        const val PORT: String = "port";
        const val ARRIVAL_DATE: String = "arrival_date";
        const val ARRIVAL_LEFT: String = "arrival_left";
        const val DEPARTURE_DATE: String = "departure_date";
        const val DEPARTURE_LEFT: String = "departure_left";
    }

    val mData : ArrayList<ListDataModel> = ArrayList();
    val mFavorites : MutableSet<Int> = mutableSetOf();

    override fun onCreate(): Boolean {
        mData.add(ListDataModel("Ship Test", "Port Test", Date(123123), 3, Date(123132), 15));
        mData.add(ListDataModel("Ship Test", "Port Test", Date(123123), 3, Date(123132), 15));
        mData.add(ListDataModel("Ship Test", "Port Test", Date(123123), 3, Date(123132), 15));
        mData.add(ListDataModel("Ship Test Extra", "Port Test", Date(123123), 3, Date(123132), 15));
        mData.add(ListDataModel("Ship Test", "Port Test", Date(123123), 3, Date(123132), 15));
        mData.add(ListDataModel("Ship Test", "Port Test", Date(123123), 3, Date(123132), 15));
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
        when (uri) {
            LIST_DATA_URI -> {
                val cursor = MatrixCursor(
                    arrayOf(
                        Columns.ID,
                        Columns.VESSEL,
                        Columns.PORT,
                        Columns.ARRIVAL_DATE,
                        Columns.ARRIVAL_LEFT,
                        Columns.DEPARTURE_DATE,
                        Columns.DEPARTURE_LEFT));

                mData.forEachIndexed { index, element ->
                    cursor.addRow(arrayOf(
                        index,
                        element.mVessel,
                        element.mPort,
                        element.mArrivalDate,
                        element.mArrivalLeft,
                        element.mDepartureDate,
                        element.mDepartureLeft));
                }
                return cursor;
            }
            FAVORITES_DATA_URI -> {
                val cursor = MatrixCursor(arrayOf(Columns.ID));
                mFavorites.forEach { element ->
                    cursor.addRow(arrayOf(element));
                }
                return cursor;
            }
        }
        return null;
    }

    override fun getType(uri: Uri): String? {
        return null;
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values != null) {
            when (uri) {
                LIST_DATA_URI -> {
                    if (mData.add(ListDataModel(
                            values.getAsString(Columns.VESSEL),
                            values.getAsString(Columns.PORT),
                            Date(values.getAsLong(Columns.ARRIVAL_DATE)),
                            values.getAsInteger(Columns.ARRIVAL_LEFT),
                            Date(values.getAsLong(Columns.DEPARTURE_DATE)),
                            values.getAsInteger(Columns.DEPARTURE_LEFT)
                        ))) {
                        context?.contentResolver?.notifyChange(uri, null);
                        val id: Long = mData.lastIndex.toLong();
                        return ContentUris.withAppendedId(LIST_DATA_URI, id);
                        }
                }
                FAVORITES_DATA_URI -> {
                    val id = values.getAsInteger(Columns.ID);
                    if (mFavorites.add(id)) {
                        context?.contentResolver?.notifyChange(uri, null);
                        return ContentUris.withAppendedId(FAVORITES_DATA_URI, id.toLong());
                    }
                }
            }
        }
        return null;
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        val id: Int = Integer.parseInt(uri.pathSegments[0]);
        when (uri) {
            LIST_DATA_URI -> {
                mData.removeAt(id);
                context?.contentResolver?.notifyChange(uri, null);
                return 1;
            }
            FAVORITES_DATA_URI -> {
                if (mFavorites.remove(id)) {
                        context?.contentResolver?.notifyChange(uri, null);
                        return 1;
                }
            }
        }
        return 0;
    }

    override fun update(uri: Uri,
                        values: ContentValues?,
                        selection: String?,
                        selectionArgs: Array<out String>?): Int {
        val id: Int = Integer.parseInt(uri.pathSegments[0]);
        if (values != null) {
            when (uri) {
                LIST_DATA_URI -> {
                    mData[id] = ListDataModel(
                        values.getAsString(Columns.VESSEL),
                        values.getAsString(Columns.PORT),
                        Date(values.getAsLong(Columns.ARRIVAL_DATE)),
                        values.getAsInteger(Columns.ARRIVAL_LEFT),
                        Date(values.getAsLong(Columns.DEPARTURE_DATE)),
                        values.getAsInteger(Columns.DEPARTURE_LEFT)
                    );
                    context?.contentResolver?.notifyChange(uri, null);
                    return 1;
                }
                FAVORITES_DATA_URI -> {
                    if (mFavorites.remove(id) && mFavorites.add(id)) {
                        context?.contentResolver?.notifyChange(uri, null);
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
}