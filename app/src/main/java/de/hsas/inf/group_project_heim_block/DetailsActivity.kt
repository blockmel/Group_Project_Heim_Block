package de.hsas.inf.group_project_heim_block

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hsas.inf.group_project_heim_block.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateDetails.setOnClickListener {
            val currentUser = intent.getStringExtra("email")
            val name = binding.nameEdit.text.toString()
            val course = binding.courseEdit.text.toString()
            val year = binding.yearEdit.text.toString()

            val userData = hashMapOf(
                "name" to name,
                "course" to course,
                "year" to year
            )

            if (currentUser != null) {
                db.collection("users").document(currentUser)
                    .set(userData)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            }
        }

    }
}