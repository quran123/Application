package com.example.application

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.application.db.QuizHelper
import com.example.application.helper.mapCursorToArrayList


class CustomAdapter(private val mList: List<ItemViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var questionHelper: QuizHelper
    var onItemClick : ((ItemViewModel) -> Unit)? = null
    private var user_name: String? = null
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
        questionHelper = QuizHelper.getInstance(parent.context)
        questionHelper.open()
        user_name= (parent.context as Activity?)?.intent?.getStringExtra(Constants.USER_NAME).toString()
        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel  = mList[position]
        holder.imageView.setImageResource(itemsViewModel.image)
        holder.textView.text = itemsViewModel.text
        holder.itemView.setOnClickListener(){
            val result = questionHelper.queryAll()
            val quotes = mapCursorToArrayList(result)
            val name=quotes.get(position).quiz_name.toString()
            val quizq=questionHelper.queryByQuizName(name)
            val questionlist = helper.mapCursorToQuestionArrayList(quizq);

            val intent = Intent(it.context, HomeActivity::class.java)
                    intent.putExtra(Constants.QuestionsList, questionlist)
                    intent.putExtra(Constants.USER_NAME,user_name)
                    intent.putExtra(Constants.QUIZ_NAME,name)
            it.context.startActivity(intent)

            onItemClick?.invoke(itemsViewModel)}
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)

    }
}