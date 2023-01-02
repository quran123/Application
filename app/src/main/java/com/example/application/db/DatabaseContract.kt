package com.example.application.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class QuestionColumns : BaseColumns {
        companion object {
            const val DATABASENAME = "QUIZ"
            const val TABLE_NAME = "QUESTION"
            const val _ID = "_id"
            const val QUIZ_NAME = "QUIZ_NAME"
            const val COL_QUESTION = "question"
            const val COL_OPTION1 = "option1"
            const val COL_OPTION2 = "option2"
            const val COL_OPTION3 = "option3"
            const val COL_OPTION4 = "option4"
            const val COL_ANSWER = "correctAnswer"
            const val DATE = "date"
        }

    }
    internal class QuizColumns : BaseColumns {
        companion object {
            const val DATABASENAME = "Question"
            const val TABLE_NAME = "TABLE_QUIZ"
            const val _ID = "_id"
            const val QUIZ_NAME = "QUIZ_NAME"
            const val DATE = "date"
        }
    }
    internal class HistoryColumns : BaseColumns {
        companion object {
            const val DATABASENAME = "QUIZ"
            const val TABLE_NAME = "History"
            const val _ID = "_id"
            var USER_NAME = "USER_NAME"
            const val QUIZ_NAME = "QUIZ_NAME"
            const val COL_QUESTION = "question"
            const val COL_OPTION1 = "option1"
            const val COL_OPTION2 = "option2"
            const val COL_OPTION3 = "option3"
            const val COL_OPTION4 = "option4"
            const val IS_CORRECT = "IS_CORRECT"
            const val OPTION_SELECTED = "OPTION_SELECTED"
            const val DATE = "date"
        }
    }
    internal class HistoryListColumns : BaseColumns {
        companion object {
            const val DATABASENAME = "QUIZ"
            const val TABLE_NAME = "History_LIST"
            const val _ID = "_id"
            var USER_NAME = "USER_NAME"
            const val QUIZ_NAME = "QUIZ_NAME"
            const val TOTAL_QUESTION = "TOTAL_QUESTION"
            const val CORRECT_QUESTION = "CORRECT_QUESTION"
            const val SCORE = "SCORE"
            const val DATE = "date"
        }
    }

}