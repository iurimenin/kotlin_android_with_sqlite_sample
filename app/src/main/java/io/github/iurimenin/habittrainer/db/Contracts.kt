package io.github.iurimenin.habittrainer.db

import android.provider.BaseColumns

/**
 * Created by Iuri Menin on 07/12/2017.
 */

val DATABASE_NAME = "habittrainer.db"
val DATABASE_VERSION = 10

object HabitEntry : BaseColumns {
    val TABLE_NAME = "habit"
    val TITLE_COL = "title"
    val DESCR_COL = "description"
    val IMAGE_COL = "image"
}