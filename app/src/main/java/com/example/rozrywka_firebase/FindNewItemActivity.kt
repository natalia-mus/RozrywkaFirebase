package com.example.rozrywka_firebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.rozrywka_firebase.API.APIData
import com.example.rozrywka_firebase.API.TvShow
import com.example.rozrywka_firebase.Adapters.FindRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_find_new_item.*

class FindNewItemActivity : AppCompatActivity () {

    private val foundMovieList: MutableList<TvShow> = mutableListOf()
    private lateinit var findAdapter: FindRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_new_item)

        findAdapter =
            FindRecyclerViewAdapter(
                foundMovieList
            )

        found_movies_recycler_view.layoutManager = LinearLayoutManager(this)
        found_movies_recycler_view.adapter = findAdapter


        findItem_search_button.setOnClickListener() {

            search()
        }

    }





    fun search () {


        val title_to_find = findItem_title.text.toString()

        if (title_to_find == "") {
            Toast.makeText(applicationContext, "Podaj tytuł", Toast.LENGTH_SHORT).show()
        }

        else {
            foundMovieList.clear()

            AndroidNetworking.initialize(this)
            AndroidNetworking.get("https://www.episodate.com/api/search?q=" + title_to_find)
                .build()
                .getAsObject(APIData::class.java, object : ParsedRequestListener<APIData> {

                    override fun onResponse(response: APIData?) {

                        foundMovieList.addAll(response!!.tvShows)
                        if(foundMovieList.size == 0) {
                            Toast.makeText(applicationContext, "Nie znaleziono pasujących wyników", Toast.LENGTH_SHORT).show()
                        }
                            findAdapter.notifyDataSetChanged()
                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(
                            applicationContext,
                            "Wyszukiwanie nie powiodło się.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}