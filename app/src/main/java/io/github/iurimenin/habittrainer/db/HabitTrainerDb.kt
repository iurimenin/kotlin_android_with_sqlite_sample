package io.github.iurimenin.habittrainer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import io.github.iurimenin.habittrainer.db.HabitEntry.DESCR_COL
import io.github.iurimenin.habittrainer.db.HabitEntry.IMAGE_COL
import io.github.iurimenin.habittrainer.db.HabitEntry.TABLE_NAME
import io.github.iurimenin.habittrainer.db.HabitEntry.TITLE_COL

/**
 * Created by Iuri Menin on 07/12/2017.
 */
class HabitTrainerDb(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE $TABLE_NAME (" +
            "$_ID INTEGER PRIMARY KEY," +
            "$TITLE_COL TEXT," +
            "$DESCR_COL TEXT," +
            "$IMAGE_COL BLOB" +
            ")"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}