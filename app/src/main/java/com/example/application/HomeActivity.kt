package com.example.application

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.application.db.DatabaseContract
import com.example.application.db.HistoryHelper
import com.example.application.db.HistoryListHelper
import com.example.application.model.Questions
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var historyHelper: HistoryHelper
    private lateinit var historyListHelper: HistoryListHelper
    private var mCurrentPosition: Int = 1
    private var mSelectedPosition: Int = 0
    private var mQuestionList: ArrayList<Questions>? = null
    private var correctAnswers:Int = 0
    private var mUserName: String? = null
    private var quiz_name: String? = null
    private val values = ContentValues()
    private val valuesList = ContentValues()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        historyHelper = HistoryHelper.getInstance(applicationContext)
        historyHelper.open()
        historyListHelper = HistoryListHelper.getInstance(applicationContext)
        historyListHelper.open()
        mQuestionList=intent.getParcelableArrayListExtra(Constants.QuestionsList)
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        values.put(DatabaseContract.HistoryColumns.USER_NAME, mUserName)
        valuesList.put(DatabaseContract.HistoryListColumns.USER_NAME, mUserName)
        historyHelper.create(values)
        quiz_name = intent.getStringExtra(Constants.QUIZ_NAME)
        values.put(DatabaseContract.HistoryColumns.QUIZ_NAME, quiz_name)
        valuesList.put(DatabaseContract.HistoryListColumns.QUIZ_NAME, quiz_name)

        setQuestion()
        tv_option1.setOnClickListener(this)
        tv_option2.setOnClickListener(this)
        tv_option3.setOnClickListener(this)
        tv_option4.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun setQuestion() {

        defaultOptionView()
        tv_option1.setClickable(true);
        tv_option2.setClickable(true);
        tv_option3.setClickable(true);
        tv_option4.setClickable(true);
        btn_submit.text = "SUBMIT"

        val question: Questions = mQuestionList!![mCurrentPosition - 1]
        tv_question.text = question.question
        progress_bar.progress = mCurrentPosition
        progress_bar.max=mQuestionList!!.size
        tv_progress.text = "$mCurrentPosition/${mQuestionList!!.size}"
        tv_option1.text = question.option1
        tv_option2.text = question.option2
        tv_option3.text = question.option3
        tv_option4.text = question.option4

        values.put(DatabaseContract.HistoryColumns.COL_QUESTION, question.question)
        values.put(DatabaseContract.HistoryColumns.COL_OPTION1,question.option1)
        values.put(DatabaseContract.HistoryColumns.COL_OPTION2, question.option2)
        values.put(DatabaseContract.HistoryColumns.COL_OPTION3, question.option3)
        values.put(DatabaseContract.HistoryColumns.COL_OPTION4, question.option4)

    }

    fun defaultOptionView() {
        val options = ArrayList<TextView>()
        options.add(0, tv_option1)
        options.add(1, tv_option2)
        options.add(2, tv_option3)
        options.add(3, tv_option4)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option1 -> {
                selectedOptionView(tv_option1, 1)
            }
            R.id.tv_option2 -> {
                selectedOptionView(tv_option2, 2)
            }
            R.id.tv_option3 -> {
                selectedOptionView(tv_option3, 3)
            }
            R.id.tv_option4 -> {
                selectedOptionView(tv_option4, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                           val intent = Intent( this, ResultActivity::class.java)
                            valuesList.put(DatabaseContract.HistoryListColumns.SCORE,(correctAnswers/mQuestionList!!.size)*100 )
                            valuesList.put(DatabaseContract.HistoryListColumns.DATE, helper.getCurrentDate())
                            valuesList.put(DatabaseContract.HistoryListColumns.TOTAL_QUESTION,mQuestionList!!.size)
                            valuesList.put(DatabaseContract.HistoryListColumns.CORRECT_QUESTION,correctAnswers)
                            val result = historyListHelper.insert(valuesList)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                            startActivity(intent)
                            finish()

                        }

                    }
                }else {
                    val questions: Questions = mQuestionList!![mCurrentPosition-1]
                    val character = questions.correctAnswer?.toCharArray()?.get(0)
                    selectedNumber(mSelectedPosition)
                    if(character?.code?.equals(mSelectedPosition+64) == false){
                        values.put(DatabaseContract.HistoryColumns.IS_CORRECT,0 )
                        answerView(mSelectedPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        correctAnswers++
                        values.put(DatabaseContract.HistoryColumns.IS_CORRECT,1 )
                        answerView(mSelectedPosition, R.drawable.correct_option_border_bg)

                    }
                    historyWrite()

                    if (mCurrentPosition == mQuestionList!!.size){
                        btn_submit.text = "FINISH"
                    }else{
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedPosition = 0
                    tv_option1.setClickable(false);
                    tv_option2.setClickable(false);
                    tv_option3.setClickable(false);
                    tv_option4.setClickable(false);
                }

            }

        }
    }
    fun selectedNumber(option:Int){
        if(option.equals(1))
            values.put(DatabaseContract.HistoryColumns.OPTION_SELECTED,"A" )
        else if(option.equals(2))
            values.put(DatabaseContract.HistoryColumns.OPTION_SELECTED,"B" )
        else if (option.equals(3))
            values.put(DatabaseContract.HistoryColumns.OPTION_SELECTED,"C" )
        else
            values.put(DatabaseContract.HistoryColumns.OPTION_SELECTED,"D" )

    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun historyWrite(){
        values.put(DatabaseContract.HistoryColumns.DATE, helper.getCurrentDate())
        values.put(DatabaseContract.HistoryListColumns.DATE, helper.getCurrentDate())
        println("hello")
        print(values)
        val result = historyHelper.insert(values)
    }
    private fun answerView(answer: Int, drawable: Int) {
        when (answer) {
            1 -> {
                tv_option1.background = ContextCompat.getDrawable(this, drawable)
            }
            2-> {
                tv_option2.background = ContextCompat.getDrawable(this, drawable)
            }
            3 -> {
                tv_option3.background = ContextCompat.getDrawable(this, drawable)
            }
            4 -> {
                tv_option4.background = ContextCompat.getDrawable(this, drawable)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionView()
        mSelectedPosition = selectedOptionNumber
        tv.setTextColor(Color.parseColor("#000000"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

    }
}