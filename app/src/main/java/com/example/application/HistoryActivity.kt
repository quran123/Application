package com.example.application

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.application.adapters.HistoryAdapter
import com.example.application.databinding.FragmentHistoryBinding
import com.example.application.db.HistoryHelper
import com.example.application.db.HistoryListHelper
import com.example.application.model.HistoryList
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.noQuizText
import kotlinx.android.synthetic.main.quizlist.*
import kotlin.properties.Delegates

class HistoryActivity: AppCompatActivity() {
    private val historyList = ArrayList<HistoryList>()
    private var historyListSize by Delegates.notNull<Int>()
    private lateinit var historyHelper: HistoryHelper
    private lateinit var historyListHelper: HistoryListHelper
    private lateinit var binding: FragmentHistoryBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_history)
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        historyHelper = HistoryHelper.getInstance(applicationContext)
        historyHelper.open()
        historyListHelper = HistoryListHelper.getInstance(applicationContext)
        historyListHelper.open()
        recyclerviewhistory.layoutManager = LinearLayoutManager(this)
        val result = historyListHelper.queryAll()
        val quotes = helper.mapCursorToHistoryList(result)

        historyListSize = quotes.size
        if (historyListSize == 0)
            makeTextViewVisible()
        else
            noQuizText.visibility = View.INVISIBLE
        val adapter = HistoryAdapter(
            quotes
        )
        recyclerviewhistory.adapter = adapter
    }
    fun makeTextViewVisible() {
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val layoutParams = LinearLayout.LayoutParams(width, height)
        noQuizText.layoutParams = layoutParams
        noQuizText.textSize = 32f
    }
}