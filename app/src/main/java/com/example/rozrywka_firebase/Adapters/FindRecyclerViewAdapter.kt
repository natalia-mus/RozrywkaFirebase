package com.example.rozrywka_firebase.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.rozrywka_firebase.API.TvShow
import com.example.rozrywka_firebase.AddActivities.AddNewItemActivity
import com.example.rozrywka_firebase.R
import kotlinx.android.synthetic.main.found_movie_item.view.*

class FindRecyclerViewAdapter (private val foundMovieList: MutableList<TvShow>) : RecyclerView.Adapter<FindHolder> () {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindHolder {
        context = parent.context
        return FindHolder(
            LayoutInflater.from(context).inflate(
                R.layout.found_movie_item,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = foundMovieList.size


    override fun onBindViewHolder(holder: FindHolder, position: Int) {

        val foundMovie = foundMovieList[position]

        val title = foundMovie.name
        val country = foundMovie.country
        val network = foundMovie.network

        holder.itemView.foundMovieTitle.setText(title)
        holder.itemView.foundMovieCountry.setText(country)
        holder.itemView.foundMovieNetwork.setText(network)




        holder.foundMovieButtonAdd.setOnClickListener {

            val intent = Intent(context, AddNewItemActivity::class.java).apply {putExtra("item_title", foundMovieList[position].name)}.apply {putExtra("item_country", foundMovieList[position].country)}.apply {putExtra("item_network", foundMovieList[position].network)}
            context.startActivity(intent)
        }
    }
}






class FindHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    val foundMovieButtonAdd = itemView.findViewById<Button>(R.id.foundMovieButtonAdd)
}