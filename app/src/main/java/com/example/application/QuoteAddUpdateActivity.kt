package com.example.application

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.application.databinding.ActivityQuoteAddUpdateBinding
import com.example.application.db.DatabaseContract
import com.example.application.db.DatabaseContract.QuizColumns.Companion.DATE
import com.example.application.db.QuoteHelper
import com.example.application.helper.EXTRA_POSITION
import com.example.application.helper.EXTRA_QUOTE
import com.example.application.helper.RESULT_ADD
import com.example.application.helper.getCurrentDate
import com.example.application.model.Questions

import kotlinx.android.synthetic.main.activity_quote_add_update.*

class QuoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private var item: Questions? = null
    private var position: Int = 0
    private lateinit var questionHelper: QuoteHelper
    private lateinit var binding: ActivityQuoteAddUpdateBinding
    private lateinit var radioButton: RadioButton
    private lateinit var invent: Intent
    private lateinit var quiz_name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_add_update)
        binding = ActivityQuoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        questionHelper = QuoteHelper.getInstance(applicationContext)
        questionHelper.open()
        val actionBarTitle = "Add Data"
        invent= getIntent()
        quiz_name  = invent.getStringExtra("quiz_name").toString()
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnNext.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(selectedOption)
            Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
            val answer=radioButton.text
            val question = binding.edtQuestion.text.toString().trim()
            val option1 = binding.edtOption1.text.toString().trim()
            val option2 = binding.edtOption2.text.toString().trim()
            val option3 = binding.edtOption3.text.toString().trim()
            val option4 = binding.edtOption4.text.toString().trim()

            if (question.isEmpty()) {
                binding.edtQuestion.error = "Field can not be blank"
                return
            }

            if (option1.isEmpty()) {
                binding.edtOption1.error = "Field can not be blank"
                return
            }
            if (option2.isEmpty()) {
                binding.edtOption2.error = "Field can not be blank"
                return
            }
            if (option3.isEmpty()) {
                binding.edtOption3.error = "Field can not be blank"
                return
            }
            if (option4.isEmpty()) {
                binding.edtOption4.error = "Field can not be blank"
                return
            }
            item?.question = question
            item?.quiz_name=quiz_name
            item?.correctAnswer = answer as String?
            item?.option1 = option1
            item?.option2 = option2
            item?.option3 = option3
            item?.option4 = option4
            val intent = Intent()
            intent.putExtra(EXTRA_QUOTE, item?.question)
            intent.putExtra(EXTRA_POSITION, position)
            val values = ContentValues()
            values.put(DatabaseContract.QuestionColumns.COL_QUESTION, question)
            values.put(DatabaseContract.QuestionColumns.QUIZ_NAME,quiz_name)
            values.put(DatabaseContract.QuestionColumns.COL_ANSWER, answer)
            values.put(DatabaseContract.QuestionColumns.COL_OPTION1, option1)
            values.put(DatabaseContract.QuestionColumns.COL_OPTION2, option2)
            values.put(DatabaseContract.QuestionColumns.COL_OPTION3, option3)
            values.put(DatabaseContract.QuestionColumns.COL_OPTION4, option4)
                item?.date = getCurrentDate()
                values.put(DATE, getCurrentDate())
                val result = questionHelper.insert(values)
                if (result > 0) {
                    item?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    if (v?.id == R.id.btn_submit) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else if (v?.id == R.id.btnNext) {
                        edt_question.text.clear()
                        edt_option1.text.clear()
                        edt_option2.text.clear()
                        edt_option3.text.clear()
                        edt_option4.text.clear()

                    }
                }
    }
}