package com.example.rozrywka_firebase.SingleItemActivities

class SingleGame (val id: String, val title: String, val type: String, val year: String, val played: String){

    constructor(): this("","", "", "", "") {}
}