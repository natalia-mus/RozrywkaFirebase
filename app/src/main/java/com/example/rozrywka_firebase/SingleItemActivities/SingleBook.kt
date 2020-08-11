package com.example.rozrywka_firebase.SingleItemActivities

class SingleBook (val id: String, val title: String, val author: String, val genre: String, val year: String, val read: String){

    constructor(): this("","", "", "", "", "") {}          // pusty konstruktor
}