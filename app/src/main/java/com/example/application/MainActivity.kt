package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.application.ui.main.CreateQuizFragment
import com.example.application.ui.main.ParticipateQuizFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title=resources.getString(R.string.app_name)
        loadFragment(ParticipateQuizFragment())
        setUpListeners()

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.create-> {
                    title=resources.getString(R.string.tab_text_1)
                    loadFragment(CreateQuizFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.participate-> {
                    title=resources.getString(R.string.tab_text_2)
                    loadFragment(ParticipateQuizFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun setUpListeners() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_history -> {
                    val intent = Intent( this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

}