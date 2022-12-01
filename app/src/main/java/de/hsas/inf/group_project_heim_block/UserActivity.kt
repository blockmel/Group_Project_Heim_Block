package de.hsas.inf.group_project_heim_block

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)

        val rank: TextView = findViewById(R.id.user_rank)
        var text: String = rank.text as String + " " + intent.getStringExtra("rank")
        rank.text = text

        val name: TextView = findViewById(R.id.user_name)
        text = name.text as String + " " + intent.getStringExtra("name")
        name.text = text

        val course: TextView = findViewById(R.id.user_course)
        text = course.text as String + " " + intent.getStringExtra("course")
        course.text = text

        val year: TextView = findViewById(R.id.user_year)
        text = year.text as String + " " + intent.getStringExtra("year")
        year.text = text

        val score: TextView = findViewById(R.id.user_score)
        text = score.text as String + " " + intent.getStringExtra("score")
        score.text = text

        val back : Button = findViewById(R.id.button_back_to_rank)
        back.setOnClickListener {
            finish()
        }
    }
}