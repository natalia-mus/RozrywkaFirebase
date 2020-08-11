package com.example.rozrywka_firebase.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.rozrywka_firebase.DeleteActivities.DeleteItemActivity
import com.example.rozrywka_firebase.EditActivities.EditItemActivity
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.SingleItemActivities.SingleMovie
import kotlinx.android.synthetic.main.movie_item.view.*


class RecyclerViewAdapter (private val movieList: MutableList<SingleMovie>): RecyclerView.Adapter<MyHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return MyHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val movie = movieList[position]

        val textViewTitle = movie.title
        val textViewCountry = movie.country
        val textViewNetwork = movie.network
        val textViewSeen = movie.seen

        holder.itemView.movieTitle.setText(textViewTitle)
        holder.itemView.movieCountry.setText(textViewCountry)
        holder.itemView.movieNetwork.setText(textViewNetwork)
        holder.itemView.movieSeen.setText(textViewSeen)


        holder.buttonDelete.setOnClickListener() {

            val intent = Intent(context, DeleteItemActivity::class.java).apply {putExtra("item_id", movieList[position].id)}
            context.startActivity(intent)
        }



        holder.buttonEdit.setOnClickListener() {

            val intent = Intent(context, EditItemActivity::class.java).apply {putExtra("item_id", movieList[position].id)}
            context.startActivity(intent)
        }
    }




}









class MyHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    var buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
    var buttonEdit = itemView.findViewById<Button>(R.id.buttonEdit)
}