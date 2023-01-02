package com.example.application.ui.main

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.application.QuoteAddUpdateActivity
import com.example.application.R
import com.example.application.db.DatabaseContract
import com.example.application.db.QuizHelper
import com.example.application.db.QuoteHelper
import com.example.application.helper
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.fragment_participate_quiz.*

class QuizActivity : AppCompatActivity() {
    private lateinit var questionHelper: QuoteHelper
    private lateinit var quizHelper: QuizHelper
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        questionHelper = QuoteHelper.getInstance(applicationContext)
        questionHelper.open()

        quizHelper = QuizHelper.getInstance(applicationContext)
        quizHelper.open()

        create_quiz.setOnClickListener {
            if ( quizname.editableText.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Please enter your name", Toast.LENGTH_LONG).show()

            } else {
                var name = quizname.editableText.toString()
                val i = Intent(this, QuoteAddUpdateActivity::class.java)
                i.putExtra("quiz_name", name)
                name = name.replace("\\s".toRegex(), "")
                println(name)
                val values = ContentValues()
                values.put(DatabaseContract.QuestionColumns.TABLE_NAME, name)
                val valuesQuiz = ContentValues()
                valuesQuiz.put(DatabaseContract.QuestionColumns.DATE, helper.getCurrentDate())
                valuesQuiz.put(DatabaseContract.QuizColumns.QUIZ_NAME, name)
                val resultQuiz = quizHelper.insert(valuesQuiz)
                val result = questionHelper.create(values)
                startActivity(i)
            }
        }

        }
}