package de.hsas.inf.group_project_heim_block

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore


// !!!       create a first user
        /*// Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/


// !!!       create a second user
        /*// Create a new user with a first, middle, and last name
        val user = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/


// !!!       Read data from database
        /*db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }*/


// !!!       create multiple users at ones
        /*val usersCollection = db.collection("users")

        // Create a new user with a first and last name
        val user1 = User("Ada1", "Lovelace", 1915)
        val user2 = User("Ada2", "Lovelace", 1915)
        val user3 = User("Ada3", "Lovelace", 1915)
        val user4 = User("Ada4", "Lovelace", 1915)
        val user5 = User("Ada5", "Lovelace", 1915)

        arrayOf(user1, user2, user3, user4, user5).map { user ->
            val name = "${user.fname} ${user.lname}"
            usersCollection.add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "User $name added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding user $name", e)
                }
        }*/

    }
}


/*
class User(
    var fname: String,
    var lname: String,
    var born: Int,
)*/