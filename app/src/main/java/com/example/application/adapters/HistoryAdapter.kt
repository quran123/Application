package com.example.application.adapters

import android.content.Intent
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.application.Constants
import com.example.application.HistoryQuestions
import com.example.application.R
import com.example.application.db.HistoryHelper
import com.example.application.db.HistoryListHelper
import com.example.application.model.HistoryList
import kotlinx.android.synthetic.main.card_history_quiz.*

class HistoryAdapter (
    private val historyList: ArrayList<HistoryList>
)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private lateinit var historyHelper: HistoryHelper
    private lateinit var historyListHelper: HistoryListHelper
    var onItemClick: (() -> Unit)? = null
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_history_quiz, parent, false)
        historyHelper = HistoryHelper.getInstance(parent.context)
        historyHelper.open()
        historyListHelper = HistoryListHelper.getInstance(parent.context)
        historyListHelper.open()
        return ViewHolder(view)
    }

    // binds the list items to a view
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(R.drawable.ic_history)
        holder.textView_title.text =
            historyList[position].quiz_name + " by " + historyList[position].user_name
        holder.textView_score.text = historyList[position].score.toString() + " % "


        holder.itemView.setOnClickListener() {

//            val result = historyHelper.queryAll(historyList[position].user_name.toString())
//            val historyQuestionList = helper.mapCursorToHistory(result);

            val intent = Intent(it.context, HistoryQuestions::class.java)
//            intent.putExtra(Constants.HistoryQuestionsList, historyQuestionList)
            intent.putExtra(Constants.History,historyList[position] as Parcelable)
            intent.putExtra(Constants.USER_NAME,historyList[position].user_name)
            intent.putExtra(Constants.CorrectQuestions,historyList[position].correct_question)
            intent.putExtra(Constants.TotalQuestions,historyList[position].total_question)
            intent.putExtra(Constants.DATE,historyList[position].date)
            intent.putExtra(Constants.SCORE,historyList[position].score.toString())
            it.context.startActivity(intent)

            onItemClick?.invoke()}
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView_categoryIcon)
        val textView_title: TextView = itemView.findViewById(R.id.textView_title)
        val textView_score: TextView = itemView.findViewById(R.id.textView_score)

    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}