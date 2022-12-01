package de.hsas.inf.group_project_heim_block

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hsas.inf.group_project_heim_block.databinding.ActivityRankBinding

const val RANK_NAME = "rank name"

class RankActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRankBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        binding.leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.leaderboardRecyclerView.adapter = RankAdapter(rankStrings, this)

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
}