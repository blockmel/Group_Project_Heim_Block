package de.hsas.inf.group_project_heim_block

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankAdapter(val listItems: Array<User>, val context: Context) : RecyclerView.Adapter<RankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.rank_recycler_template, parent, false)
        return RankViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.rank).text = (position+1).toString()
        holder.itemView.findViewById<TextView>(R.id.name).text = listItems[position].name
        holder.itemView.findViewById<TextView>(R.id.score).text = listItems[position].score
        holder.itemView.setOnClickListener {
            val intent = Intent(context, UserActivity()::class.java)
            intent.putExtra("rank", (position+1).toString())
            intent.putExtra("name", listItems[position].name)
            intent.putExtra("score", listItems[position].score)
            intent.putExtra("course", listItems[position].course)
            intent.putExtra("year", listItems[position].year)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}

class RankViewHolder(val v: View): RecyclerView.ViewHolder(v)