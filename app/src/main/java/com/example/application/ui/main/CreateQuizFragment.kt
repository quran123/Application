package com.example.application.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.application.R

class CreateQuizFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_create_quiz, container, false)
        val button2 : Button = root.findViewById<Button>(R.id.start)
        button2.setOnClickListener{
            val i = Intent(activity, QuizActivity::class.java)
            startActivity(i)
            (activity as Activity?)?.overridePendingTransition(0, 0)
        }
        return root
    }
}