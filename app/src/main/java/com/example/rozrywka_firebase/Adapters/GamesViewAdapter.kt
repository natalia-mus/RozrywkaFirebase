package com.example.rozrywka_firebase.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.rozrywka_firebase.DeleteActivities.DeleteGameActivity
import com.example.rozrywka_firebase.EditActivities.EditGameActivity
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.SingleItemActivities.SingleGame
import kotlinx.android.synthetic.main.game_item.view.*



class GamesViewAdapter (private val gameList: MutableList<SingleGame>): RecyclerView.Adapter<GameHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        context = parent.context
        return GameHolder(
            LayoutInflater.from(context).inflate(
                R.layout.game_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(holder: GameHolder, position: Int) {

        val game = gameList[position]

        val textViewTitle = game.title
        val textViewType = game.type
        val textViewYear = game.year
        val textViewPlayed = game.played

        holder.itemView.gameTitle.setText(textViewTitle)
        holder.itemView.gameType.setText(textViewType)
        holder.itemView.gameYear.setText(textViewYear)
        holder.itemView.gamePlayed.setText(textViewPlayed)


        holder.buttonDelete.setOnClickListener() {

            val intent = Intent(context, DeleteGameActivity::class.java).apply {putExtra("item_id", gameList[position].id)}
            context.startActivity(intent)
        }



        holder.buttonEdit.setOnClickListener() {

            val intent = Intent(context, EditGameActivity::class.java).apply {putExtra("item_id", gameList[position].id)}
            context.startActivity(intent)
        }
    }




}









class GameHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    var buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
    var buttonEdit = itemView.findViewById<Button>(R.id.buttonEdit)
}