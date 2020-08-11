package com.example.rozrywka_firebase.API


import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: String
)