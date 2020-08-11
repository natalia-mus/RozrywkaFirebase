package com.example.rozrywka_firebase.API


import com.google.gson.annotations.SerializedName

data class APIData(
    @SerializedName("tv_shows")
    val tvShows: List<TvShow>
)