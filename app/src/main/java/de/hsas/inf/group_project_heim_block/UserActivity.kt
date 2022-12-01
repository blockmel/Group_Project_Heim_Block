package de.hsas.inf.group_project_heim_block

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class UserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)

        val back : Button = findViewById(R.id.button_back_to_rank)
        back.setOnClickListener {
            finish()
        }
    }
}