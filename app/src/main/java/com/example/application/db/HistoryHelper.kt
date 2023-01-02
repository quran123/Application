package com.example.application.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class HistoryHelper (context: Context) {
    companion object {
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: HistoryHelper? = null
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context): HistoryHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HistoryHelper(context)
        }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen) database.close()
    }
    fun queryAll(values: String?): Cursor {
        val q =values
        return database.query(q , null, null, null, null, null, "${DatabaseContract.HistoryColumns._ID} ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(DatabaseContract.HistoryColumns.TABLE_NAME, null, "${DatabaseContract.HistoryColumns._ID} = ?", arrayOf(id), null, null, null, null)
    }

    fun create(values: ContentValues?) {
        val SQL_CREATE_TABLE_HISTORY = "CREATE TABLE " +values?.get(DatabaseContract.HistoryColumns.USER_NAME)+
                " (${DatabaseContract.HistoryColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.HistoryColumns.QUIZ_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.USER_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.COL_QUESTION} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.COL_OPTION1} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.COL_OPTION2} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.COL_OPTION3} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.COL_OPTION4} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.IS_CORRECT} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.OPTION_SELECTED} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryColumns.DATE} TEXT NOT NULL)"
        return database.execSQL(SQL_CREATE_TABLE_HISTORY)
    }
    fun insert(values: ContentValues?): Long {
        val q =values?.get("USER_NAME")
        return database.insert(q as String?, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DatabaseContract.HistoryColumns.TABLE_NAME, values, "${DatabaseContract.HistoryColumns._ID} = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DatabaseContract.HistoryColumns.TABLE_NAME, "${DatabaseContract.HistoryColumns._ID} = '$id'", null)
    }
}