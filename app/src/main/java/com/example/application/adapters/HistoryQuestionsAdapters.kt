package com.example.application.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.application.db.HistoryHelper
import com.example.application.model.History

class HistoryQuestionsAdapter (
    private val history: ArrayList<History>
)
    : RecyclerView.Adapter<HistoryQuestionsAdapter.ViewHolder>() {
    private lateinit var historyHelper: HistoryHelper

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_question, parent, false)
        historyHelper = HistoryHelper.getInstance(parent.context)
        historyHelper.open()
        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("Hisas")
        holder.imageView.setImageResource(R.drawable.ic_history)
        holder.textView_question.setText(history[position].question)

        if (history[position].isCorrect == 1) {
            holder.rba.setText(history[position].option1)
            holder.rbb.setText(history[position].option2)
            holder.rbc.setText(history[position].option3)
            holder.rbd.setText(history[position].option4)
            holder.imageView.setImageResource(R.drawable.ic_round_correct)
            if (history[position].optionSelected == "A") {
                holder.rba.isChecked = true
                holder.rba.setTextColor(R.color.green_dark)
            } else if (history[position].optionSelected == "B") {
                holder.rbb.setBackgroundColor(R.color.green_dark)
                holder.rbb.isChecked = true
            } else if (history[position].optionSelected == "C"){
                holder.rbc.isChecked = true
                holder.rbc.setBackgroundColor(R.color.green_dark)
            }
            else if(history[position].optionSelected=="D") {
                holder.rbd.isChecked = true
                holder.rbd.setBackgroundColor(R.color.green_dark)
            }

        } else {
            if (history[position].optionSelected == "A") {
                holder.rba.isChecked = true
                holder.rba.setBackgroundColor(R.color.red_dark)
            } else if (history[position].optionSelected == "B") {
                holder.rbb.setBackgroundColor(R.color.red_dark)
                holder.rbb.isChecked = true
            } else if (history[position].optionSelected == "C"){
                holder.rbc.isChecked = true
                holder.rbc.setBackgroundColor(R.color.red_dark)
            }
            else if(history[position].optionSelected=="D") {
                holder.rbd.isChecked = true
                holder.rbd.setBackgroundColor(R.color.red_dark)
            }
            history[position].option1.also { holder.rba.text = it }
            holder.rbb.text=history[position].option2
            holder.rbc.text = history[position].option3
            holder.rbd.text = history[position].option4
            holder.imageView.setImageResource(R.drawable.ic_round_wrong)
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView =
            itemView.findViewById(R.id.imageview_cardquestion_iconanswerstate)
        val textView_question: TextView = itemView.findViewById(R.id.txtview_cardquestion_question)
        val textView_radio_answer: RadioGroup =
            itemView.findViewById(R.id.radiogroup_cardquestion_answerchoices)
        val rba:RadioButton=itemView.findViewById(R.id.rbA)
        val rbb:RadioButton=itemView.findViewById(R.id.rbB)
        val rbc:RadioButton=itemView.findViewById(R.id.rbC)
        val rbd:RadioButton=itemView.findViewById(R.id.rbD)
    }
    override fun getItemCount(): Int {
        return history.size
    }
}