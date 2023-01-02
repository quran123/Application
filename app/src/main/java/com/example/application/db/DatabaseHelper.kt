package com.example.application.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.application.db.DatabaseContract.QuestionColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbquestionapp"
        private const val DATABASE_VERSION = 5
        private const val SQL_CREATE_TABLE_QUESTION = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.QuestionColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.QuestionColumns.QUIZ_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_QUESTION} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_ANSWER} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION1} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION2} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION3} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.COL_OPTION4} TEXT NOT NULL," +
                " ${DatabaseContract.QuestionColumns.DATE} TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_QUIZ_NAME = "CREATE TABLE ${DatabaseContract.QuizColumns.TABLE_NAME}" +
                " (${DatabaseContract.QuizColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.QuizColumns.QUIZ_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.QuizColumns.DATE} TEXT NOT NULL)"

        private  var SQL_CREATE_TABLE_HISTORY = "CREATE TABLE ${DatabaseContract.HistoryColumns.TABLE_NAME}"+
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

        private  var SQL_CREATE_TABLE_HISTORYList = "CREATE TABLE ${DatabaseContract.HistoryListColumns.TABLE_NAME}"+
                " (${DatabaseContract.HistoryListColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.HistoryListColumns.QUIZ_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryListColumns.USER_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryListColumns.TOTAL_QUESTION} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryListColumns.CORRECT_QUESTION} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryListColumns.SCORE} TEXT NOT NULL," +
                " ${DatabaseContract.HistoryListColumns.DATE} TEXT NOT NULL)"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(SQL_CREATE_TABLE_QUESTION)
            db.execSQL(SQL_CREATE_TABLE_QUIZ_NAME)
            db.execSQL(SQL_CREATE_TABLE_HISTORY)
            db.execSQL(SQL_CREATE_TABLE_HISTORYList)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        }
        onCreate(db)
    }
}