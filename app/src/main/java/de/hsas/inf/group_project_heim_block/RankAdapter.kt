package de.hsas.inf.group_project_heim_block

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RankAdapter(val listItems: Array<String>, val context: Context) : RecyclerView.Adapter<RankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.rank_recycler_template, parent, false)
        return RankViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.rank).text = listItems[position]
        holder.itemView.findViewById<TextView>(R.id.name).text = listItems[position]
        holder.itemView.findViewById<TextView>(R.id.score).text = listItems[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, UserActivity()::class.java)
//            intent.putExtra(RANK_NAME, rank.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}

class RankViewHolder(val v: View): RecyclerView.ViewHolder(v)