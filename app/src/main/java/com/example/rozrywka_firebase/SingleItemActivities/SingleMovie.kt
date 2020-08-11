package com.example.rozrywka_firebase.SingleItemActivities

class SingleMovie (val id: String, val title: String, val country: String, val network: String, val seen: String){

    constructor(): this("","", "", "", "") {}
}