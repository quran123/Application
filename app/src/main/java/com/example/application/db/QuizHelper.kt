package com.example.application.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.application.db.DatabaseContract.QuizColumns.Companion.QUIZ_NAME
import com.example.application.db.DatabaseContract.QuizColumns.Companion.TABLE_NAME
import com.example.application.db.DatabaseContract.QuizColumns.Companion._ID

class QuizHelper(context: Context) {
    companion object {
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: QuizHelper? = null
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context): QuizHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: QuizHelper(context)
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
        val SQL_CREATE_TABLE_QUESTION = "CREATE TABLE $TABLE_NAME"+
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $QUIZ_NAME TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.DATE} TEXT NOT NULL)"
        return database.execSQL(SQL_CREATE_TABLE_QUESTION)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(TABLE_NAME, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(TABLE_NAME, values, "$_ID = ?", arrayOf(id))
    }
    fun deleteRow(quizname: String): Int {
        return database.delete( TABLE_NAME, "$QUIZ_NAME = '$quizname'", null)
    }
}