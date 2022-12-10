package de.hsas.inf.group_project_heim_block

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hsas.inf.group_project_heim_block.databinding.ActivityRankBinding
import kotlin.math.abs

class RankActivity : AppCompatActivity() {
    val TAG = "RankActivity"
    val db = Firebase.firestore
    private lateinit var binding: ActivityRankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)


        //TODO refresh every minute

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                //https://stackoverflow.com/questions/35253368/how-can-i-create-an-array-in-kotlin-like-in-java-by-just-providing-a-size
                var arrayWithScores: Array<User> = Array(result.size()){User("","","","")}
                var arrayWithOutScores: Array<User> = Array(result.size()){User("","","","")}
                var counterScores = 0
                var counterWithoutScores = 0
                for (document in result) {
                    val score = calculateScore(document)
                    if (score != "N/A") {
                        arrayWithScores.set(
                            counterScores, User(
                                document.data.get("name").toString(),
                                document.data.get("course").toString(),
                                document.data.get("year").toString(),
                                score
                            )
                        )
                        counterScores++
                    } else {
                        arrayWithOutScores.set(counterWithoutScores, User(document.data.get("name").toString(), document.data.get("course").toString(),
                            document.data.get("year").toString(), score))
                        counterWithoutScores++
                    }
                }
                var remove = 0
                arrayWithScores.forEach {
                    if(it.score == "")
                        remove++
                }
                arrayWithScores = arrayWithScores.copyOfRange(0, arrayWithScores.size-remove)

                remove = 0
                arrayWithOutScores.forEach {
                    if(it.score == "")
                        remove++
                }
                arrayWithOutScores = arrayWithOutScores.copyOfRange(0, arrayWithOutScores.size-remove)

                arrayWithScores.sortByDescending { it.score }

                val arrayWithScoresLen = arrayWithScores.size
                val arrayWithOutScoresLen = arrayWithOutScores.size
                val arrayScores = Array<User>(arrayWithScoresLen + arrayWithOutScoresLen){User("","","","")}

                System.arraycopy(arrayWithScores, 0, arrayScores, 0, arrayWithScoresLen)
                System.arraycopy(arrayWithOutScores, 0, arrayScores, arrayWithScoresLen, arrayWithOutScoresLen)

                binding.leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.leaderboardRecyclerView.adapter = RankAdapter(arrayScores, this)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }



    private fun calculateScore(document: QueryDocumentSnapshot) : String {
        if (document.data.get("accelerometer_data") != null){
            val list: MutableList<HashMap<String, Double>> = document.data.get("accelerometer_data") as MutableList<HashMap<String, Double>>
            if(list.size > 1000)
                return "N/A"
            var sumX = 0.0
            var sumY = 0.0
            var sumZ = 0.0
            list.forEach{
                sumX += abs(it.get("x")!!)
                sumY += abs(it.get("y")!!)
                sumZ += abs(it.get("z")!!)
            }
            //TODO change to 1000
            val score = (sumX + sumY + sumZ)/10

            return score.toString()
        } else {
            return "N/A"
        }
    }
}