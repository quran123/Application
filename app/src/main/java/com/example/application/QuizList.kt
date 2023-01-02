package com.example.application

import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.db.QuizHelper
import com.example.application.db.QuoteHelper
import com.example.application.helper.mapCursorToArrayList
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.quizlist.*
import kotlinx.android.synthetic.main.quizlist.noQuizText
import kotlin.math.max

class QuizList : AppCompatActivity() {
    private lateinit var quizHelper: QuizHelper
    private lateinit var questionHelper: QuoteHelper
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizlist)
        supportActionBar?.title = "Quiz List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        quizHelper = QuizHelper.getInstance(applicationContext)
        quizHelper.open()
        questionHelper = QuoteHelper.getInstance(applicationContext)
        questionHelper.open()
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemViewModel>()
        val result = quizHelper.queryAll()
        val quotes = mapCursorToArrayList(result)

        if (quotes.size == 0) {
            makeTextViewVisible()
        } else {

            for (i in quotes) {
                val quizq = questionHelper.queryByQuizName(i.quiz_name.toString())
                val questionlist = helper.mapCursorToQuestionArrayList(quizq);
                if (questionlist.size == 0)
                    quizHelper.deleteRow(i.quiz_name.toString())
                else
                    data.add(ItemViewModel(R.drawable.ic_flag_of_argentina, "" + i.quiz_name))
            }
            if(data.size==0)
               makeTextViewVisible()

            val adapter = CustomAdapter(data)
            recyclerview.adapter = adapter
            noQuizText.visibility=View.INVISIBLE
        }
    }
    fun makeTextViewVisible(){
    val height = LinearLayout.LayoutParams.MATCH_PARENT
    val width = LinearLayout.LayoutParams.MATCH_PARENT
    val layoutParams = LinearLayout.LayoutParams(width, height)
    noQuizText.layoutParams = layoutParams
    noQuizText.textSize=32f
}
}