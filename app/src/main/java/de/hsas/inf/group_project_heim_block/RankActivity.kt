package de.hsas.inf.group_project_heim_block

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hsas.inf.group_project_heim_block.databinding.ActivityRankBinding

class RankActivity : AppCompatActivity() {
    val TAG = "RankActivity"
    val db = Firebase.firestore
    private lateinit var binding: ActivityRankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRankBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)




        //TODO change userArray to data read from Firebase
        //TODO calculate movement score
        //TODO sort users after score
        //TODO refresh every minute

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d(TAG, "${document.data.get("score")}")
                }
                //depending on the length of result do an array
                //fill with data
                //sort array

                //https://stackoverflow.com/questions/35253368/how-can-i-create-an-array-in-kotlin-like-in-java-by-just-providing-a-size
                var array: Array<User> = Array(result.size()){User("","","","")}
                var counter = 0
                for (document in result) {
                    array.set(counter, User(document.data.get("name").toString(), document.data.get("course").toString(),
                        document.data.get("year").toString(), document.data.get("score").toString()))
                    counter++
                }
//                array.set(0, user1)
//                array.set(1,user2)
                Log.d(TAG, "Array0: ${array.get(0)} ; Array1: ${array.get(1)}")
//                assignRank(result)

                array.sortBy { it.score }

                binding.leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.leaderboardRecyclerView.adapter = RankAdapter(array, this)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }



//        val rankAdapter = RankAdapter { rank -> adapterOnClick(rank) }
/*
        val recyclerView: RecyclerView = findViewById(R.id.leaderboard_recycler_view)
        recyclerView.adapter = rankAdapter*/
    }

/*    private fun adapterOnClick(rank: Rank) {
        val intent = Intent(this, UserActivity()::class.java)
        intent.putExtra(RANK_NAME, rank.name)
        startActivity(intent)
    }*/

    private fun assignRank(result: QuerySnapshot){
        val length = result.size()
        var scores = Array(length){i -> (i * i).toString()}
        Log.d(TAG, "scores: ${scores[0]}, ${scores[1]}, ${scores[2]}, ${scores[3]}")
        var counter = 0
        for (document in result) {
            document.data.get("score")
            scores[counter] = document.data.get("score") as String
            counter++
        }
        Log.d(TAG, "scores2: ${scores[0]}, ${scores[1]}, ${scores[2]}, ${scores[3]}")
        var scoresAsInt = Array(length){i -> (i * i)}
        counter = 0
        scores.forEach {
            scoresAsInt[counter] = it.toInt()
            counter++
        }
        Log.d(TAG, "scores3: ${scoresAsInt[0]}, ${scoresAsInt[1]}, ${scoresAsInt[2]}, ${scoresAsInt[3]}")
        scoresAsInt.sortDescending()
        Log.d(TAG, "scores4: ${scoresAsInt[0]}, ${scoresAsInt[1]}, ${scoresAsInt[2]}, ${scoresAsInt[3]}")
        for (document in result) {
            val userScore : String = document.data.get("score") as String
            val userScoreAsInt : Int = userScore.toInt()
            counter = 0
            var found = false
            scoresAsInt.forEach {
                Log.d(TAG, "it: ${it}; found: ${found}; userScoreAsInt: ${userScoreAsInt}; userScore: ${userScore}; counter: ${counter}")
                if (it.equals(userScoreAsInt) && !found) {
                    Log.d(TAG, "hier")
                    /*val value: String = (counter +1).toString()
                    document.data.put("rank", value)*/

                    val data = hashMapOf("rank" to (counter +1).toString())

                    db.collection("user").document(document.id)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener { Log.d(TAG, "aktualisiert") }
                        .addOnFailureListener { Log.d(TAG, "nicht aktualisiert") }


                    Log.d(TAG, "${document.data}")
                    found = true
                }
                counter++
            }
        }

        for (document in result) {
            Log.d(TAG, "${document.id} => ${document.data}")
        }
    }
}