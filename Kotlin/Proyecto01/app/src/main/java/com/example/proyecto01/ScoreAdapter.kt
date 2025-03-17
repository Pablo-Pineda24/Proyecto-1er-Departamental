package com.example.proyecto01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto01.db.ScoreModel

class ScoreAdapter(private val scoreList: ArrayList<ScoreModel>) :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewScoreId: TextView = itemView.findViewById(R.id.textViewScoreId)
        val textViewScoreValor: TextView = itemView.findViewById(R.id.textViewScoreValor)
        val textViewScoreFecha: TextView = itemView.findViewById(R.id.textViewScoreFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_score, parent, false)
        return ScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val currentScore = scoreList[position]
        holder.textViewScoreId.text = "#${currentScore.id}"
        holder.textViewScoreValor.text = "Score: ${currentScore.valor} clicks"
        holder.textViewScoreFecha.text = currentScore.fecha
    }

    override fun getItemCount() = scoreList.size

    fun updateData(newScoreList: ArrayList<ScoreModel>) {
        scoreList.clear()
        scoreList.addAll(newScoreList)
        notifyDataSetChanged()
    }
}