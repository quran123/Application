package com.example.application.ui.main

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.application.Constants
import com.example.application.QuizList
import com.example.application.R

import kotlinx.android.synthetic.main.fragment_participate_quiz.*

class ParticipateQuizFragment() : Fragment() , View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_participate_quiz, container, false)
        val button : Button = root.findViewById<Button>(R.id.btn_start)
        button.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View?) {
        if (et_input_text.text.toString().isEmpty()){
                Toast.makeText(view?.context, "Please enter your name", Toast.LENGTH_LONG).show()

            }else{
                val intent = Intent(activity, QuizList::class.java)
                intent.putExtra(Constants.USER_NAME, et_input_text.text.toString())
                startActivity(intent)
                (activity as Activity?)?.overridePendingTransition(0, 0)

            }
        }
    }