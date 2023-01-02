package com.example.application

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.application.adapters.HistoryQuestionsAdapter
import com.example.application.databinding.FragmentQuizDetailBinding
import com.example.application.db.HistoryHelper
import com.example.application.model.HistoryList
import kotlinx.android.synthetic.main.fragment_quiz_detail.*

class HistoryQuestions : AppCompatActivity() {
    private lateinit var historyHelper: HistoryHelper
    private lateinit var intentHistory: Intent
    private lateinit var history: HistoryList
    private lateinit var user_name: String
    private lateinit var binding: FragmentQuizDetailBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentQuizDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        historyHelper = HistoryHelper.getInstance(applicationContext)
        historyHelper.open()
        intentHistory= getIntent()
        history= intentHistory.getParcelableExtra(Constants.History)!!
        println("HISTORY")
        println(history)
        user_name  = history.user_name.toString()
        textView_score.text=history.score.toString()+"% SCORE"
        textView_date.text=history.date

        textView_questions.text=history.total_question.toString()+" Question"
        textView_correctAnswers.text=history.correct_question.toString()+" Correct Answers"
        textView_wrongAnswers.text=(history.total_question!! -history.correct_question!!).toString()+" InCorrect Answers"
        val result = historyHelper.queryAll(history.user_name.toString())
        val historyQuestionList = helper.mapCursorToHistory(result);
        println("History")
        println(historyQuestionList)
        recyclerView_questions.layoutManager = LinearLayoutManager(this)
        val adapter = HistoryQuestionsAdapter(historyQuestionList
        )
        println("hisa")
        recyclerView_questions.adapter = adapter
    }
}