package com.example.application

import android.annotation.SuppressLint
import android.database.Cursor
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.application.data.QuizTables
import com.example.application.db.DatabaseContract
import com.example.application.model.HistoryList
import com.example.application.model.History
import com.example.application.model.Questions
import java.util.*
import kotlin.collections.ArrayList

object helper {

    const val EXTRA_QUOTE = "extra_quote"
    const val EXTRA_POSITION = "extra_position"
    const val RESULT_ADD = 101

    fun mapCursorToQuestionArrayList(notesCursor: Cursor?): ArrayList<Questions> {
        val quotesList = ArrayList<Questions>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.QuestionColumns._ID))
                val question = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_QUESTION))
                val quizname = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.QUIZ_NAME))
                val correct_answer = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_ANSWER))
                val option1 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION1))
                val option2 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION2))
                val option3 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION3))
                val option4 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION4))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.DATE))
                quotesList.add(Questions(id, quizname, question, correct_answer, option1,option2,option3,option4, date))
            }
        }
        return quotesList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun mapCursorToArrayList(namesCursor: Cursor?): ArrayList<QuizTables> {
        val quotesList = ArrayList<QuizTables>()
        namesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.QuizColumns._ID))
                val quiz = getString(getColumnIndexOrThrow(DatabaseContract.QuizColumns.QUIZ_NAME))
                val date= getCurrentDate()
                quotesList.add(QuizTables(id,quiz,date))
            }
        }
        return quotesList
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun mapCursorToHistoryList(namesCursor: Cursor?): ArrayList<HistoryList> {
        val quotesList = ArrayList<HistoryList>()
        namesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns._ID))
                val user = getString(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns.USER_NAME))
                val quiz = getString(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns.QUIZ_NAME))
                val total_question = getInt(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns.TOTAL_QUESTION))
                val correct_question = getInt(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns.CORRECT_QUESTION))
                val score = getInt(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns.SCORE))
                val date= getCurrentDate()
                quotesList.add(HistoryList(id,user,quiz,total_question,correct_question,score,date))
            }
        }
        return quotesList
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.N)
    fun mapCursorToHistory(namesCursor: Cursor?): ArrayList<History> {
        val quotesList = ArrayList<History>()
        namesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns._ID))
                val user = getString(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns.USER_NAME))
                val quiz = getString(getColumnIndexOrThrow(DatabaseContract.HistoryListColumns.QUIZ_NAME))
                val question = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_QUESTION))
                val option1 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION1))
                val option2 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION2))
                val option3 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION3))
                val option4 = getString(getColumnIndexOrThrow(DatabaseContract.QuestionColumns.COL_OPTION4))
                val is_correct = getInt(getColumnIndex(DatabaseContract.HistoryColumns.IS_CORRECT))
                val option_selected=getString(getColumnIndexOrThrow(DatabaseContract.HistoryColumns.OPTION_SELECTED))
                val date= getCurrentDate()
                quotesList.add(History(id,user,quiz,question,option1,option2,option3,option4,is_correct,option_selected,date))
            }
        }
        return quotesList
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}