package com.example.application.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.application.db.DatabaseContract.HistoryListColumns.Companion.TABLE_NAME

class HistoryListHelper (context: Context) {
    companion object {
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: HistoryListHelper? = null
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context): HistoryListHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HistoryListHelper(context)
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

    fun queryAll(): Cursor {
        return database.query(
            DatabaseContract.HistoryListColumns.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.HistoryListColumns._ID} ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DatabaseContract.HistoryListColumns.TABLE_NAME,
            null,
            "${DatabaseContract.HistoryListColumns._ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }
    fun create() {
        val SQL_CREATE_TABLE_HistoryList =
            "CREATE TABLE ${DatabaseContract.QuizColumns.TABLE_NAME}"+
                    " (${DatabaseContract.HistoryListColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.HistoryListColumns.USER_NAME} TEXT NOT NULL," +
                    " ${DatabaseContract.HistoryListColumns.QUIZ_NAME} TEXT NOT NULL," +
                    "${DatabaseContract.HistoryListColumns.TOTAL_QUESTION} INTEGER NOT NULL,"+
                    "${DatabaseContract.HistoryListColumns.CORRECT_QUESTION} INTEGER NOT NULL,"+
                    "${DatabaseContract.HistoryListColumns.SCORE} INTEGER NOT NULL,"+
                    " ${DatabaseContract.HistoryListColumns.DATE} TEXT NOT NULL)"
        return database.execSQL(SQL_CREATE_TABLE_HistoryList)
    }

    fun insert(values: ContentValues?): Long {

        return database.insert(TABLE_NAME, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(
            DatabaseContract.HistoryListColumns.TABLE_NAME,
            values,
            "${DatabaseContract.HistoryListColumns._ID} = ?",
            arrayOf(id)
        )
    }

    fun deleteById(id: String): Int {
        return database.delete(
            DatabaseContract.HistoryListColumns.TABLE_NAME,
            "${DatabaseContract.HistoryListColumns._ID} = '$id'",
            null
        )
    }
}