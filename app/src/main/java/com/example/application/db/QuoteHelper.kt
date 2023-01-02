package com.example.application.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.application.db.DatabaseContract.QuestionColumns.Companion.QUIZ_NAME
import com.example.application.db.DatabaseContract.QuestionColumns.Companion.TABLE_NAME
import com.example.application.db.DatabaseContract.QuestionColumns.Companion._ID

class QuoteHelper(context: Context) {
    companion object {
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: QuoteHelper? = null
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context): QuoteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: QuoteHelper(context)
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
        return database.query(TABLE_NAME, null, null, null, null, null, "$_ID ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query( TABLE_NAME, null, "$_ID = ?", arrayOf(id), null, null, null, null)
    }
    fun queryByQuizName(id: String): Cursor {
        return database.query( id,
           null, null, null, null, null, null)
    }
    fun create(values: ContentValues?) {
        val SQL_CREATE_TABLE_QUESTION = "CREATE TABLE " +values?.get(DatabaseContract.QuestionColumns.TABLE_NAME)+
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $QUIZ_NAME TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_QUESTION} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_ANSWER} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION1} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION2} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION3} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION4} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.DATE} TEXT NOT NULL)"
        return database.execSQL(SQL_CREATE_TABLE_QUESTION)
        }
    fun insert(values: ContentValues?): Long {
        val q =values?.get("QUIZ_NAME")
        return database.insert(q as String?, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(TABLE_NAME, values, "$_ID = ?", arrayOf(id))
    }
    fun getAllTables(): Cursor {
        return database.query("sqlite_master", null, null, null, null, null, null)
    }
    fun deleteById(id: String): Int {
        return database.delete( TABLE_NAME, "$_ID = '$id'", null)
    }
}