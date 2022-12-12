package de.hsas.inf.group_project_heim_block

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.format.DateUtils
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hsas.inf.group_project_heim_block.databinding.ActivityRankBinding
import okhttp3.*
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
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
        getLeaderboard()

        binding.backButton.setOnClickListener {
            this.finish()
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        var time: Long = System.currentTimeMillis()
        var nextTime = time + 61000


        val client = OkHttpClient()
        val request = Request.Builder().url("https://gist.githubusercontent.com/saravanabalagi/541a511eb71c366e0bf3eecbee2dab0a/raw/bb1529d2e5b71fd06760cb030d6e15d6d56c34b3/place_types.json").build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                while (true){
                    try {
                        val now = System.currentTimeMillis()
                        val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS)
                        Handler(Looper.getMainLooper()).post {
                            val lastRefresh: TextView = findViewById(R.id.last_refreshed)
                            lastRefresh.text = "Last refreshed " + ago
                            val nextRefresh: TextView = findViewById(R.id.next_refresh)
                            nextRefresh.text = "Refreshing in " + ((nextTime - now)/1000).toString() + " seconds"
                        }
                        val oneMinute: CharSequence = "1 minute ago"
                        if (ago == oneMinute){
                            Handler(Looper.getMainLooper()).post {
                                getLeaderboard()
                            }
                            time = System.currentTimeMillis()
                            nextTime = time + 61000
                        }
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    while (true){
                        try {
                            val now = System.currentTimeMillis()
                            val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS)
                            Handler(Looper.getMainLooper()).post {
                                val lastRefresh: TextView = findViewById(R.id.last_refreshed)
                                lastRefresh.text = "Last refreshed " + ago
                                val nextRefresh: TextView = findViewById(R.id.next_refresh)
                                nextRefresh.text = "Refreshing in " + ((nextTime - now)/1000).toString() + " seconds"
                            }
                            val oneMinute: CharSequence = "1 minute ago"
                            if (ago == oneMinute){
                                Handler(Looper.getMainLooper()).post {
                                    getLeaderboard()
                                }
                                time = System.currentTimeMillis()
                                nextTime = time + 61000
                            }
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        })


    }


    private fun getLeaderboard(){
        db.collection("Users")
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
            var list: MutableList<HashMap<String, Double>> = mutableListOf()
            try{
                list = document.data.get("accelerometer_data") as MutableList<HashMap<String, Double>>
            } catch (e: Exception){
                return "N/A"
            }
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
            val score = (sumX + sumY + sumZ)/1000

            return score.toString()
        } else {
            return "N/A"
        }
    }
}