package de.hsas.inf.group_project_heim_block

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hsas.inf.group_project_heim_block.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    val db = Firebase.firestore


    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
//                    updateUI(user)

                    val userData = hashMapOf(
                        "name" to "",
                        "course" to "",
                        "year" to ""
                    )
                    db.collection("Users").document(email.substring(0,5))
                        .set(userData)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putExtra("email", email.substring(0,5))
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "email is not valid or already used.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }

    private fun authUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
//                    updateUI(user)
                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putExtra("email", email.substring(0,5))
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore


        auth = Firebase.auth
        binding.signup.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            createUser(email, password)

        }
        binding.signin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            authUser(email, password)
        }

    }
}