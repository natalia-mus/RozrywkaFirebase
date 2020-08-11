package com.example.rozrywka_firebase.AddActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rozrywka_firebase.GamesActivity
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.SingleItemActivities.SingleGame
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_new_game.*

class AddNewGameActivity : AppCompatActivity () {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_game)


        val title_exists = intent.getStringExtra("game_title")
        val type_exists = intent.getStringExtra("game_type")
        val year_exists = intent.getStringExtra("game_year")

        var title = ""
        var type = ""
        var year = ""
        var played = "NIE"


        if(title_exists != "") {
            addGame_title.setText(title_exists)
        }

        if(type_exists != "") {
            addGame_type.setText(type_exists)
        }

        if(year_exists != "") {
            addGame_year.setText(year_exists)
        }



        lateinit var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val ref = FirebaseDatabase.getInstance().getReference("users")
        var testId: Long = 0

        ref.child(auth.currentUser!!.uid).child("games").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {



                if(p0.exists()) {
                    val countItems = p0.childrenCount + 1
                    var empty: Long = 0


                    for(i in 0 until countItems) {

                        val lastChild = p0.child(i.toString()).getValue()
                        val lastChildValue = lastChild.toString()


                        if (lastChildValue == "null") {

                            empty = i
                        }


                        testId = empty
                    }
                }
            }


        })


        fun save() {

            title = addGame_title.text.toString()
            type = addGame_type.text.toString()
            year = addGame_year.text.toString()


            val game =
                SingleGame(
                    testId.toString(),
                    title,
                    type,
                    year,
                    played
                )




            if (testId != null) {
                ref.child(auth.currentUser!!.uid).child("games").child(testId.toString()).setValue(game)
            }

            Toast.makeText(applicationContext, "Dodano", Toast.LENGTH_SHORT).show()
        }



        addGame_played.setOnClickListener() {

            if(addGame_played.isChecked)
            {
                played = "TAK"
            }

            else
            {
                played = "NIE"
            }
        }




        addGame_ok.setOnClickListener() {

            if(addGame_title.text.isNullOrEmpty()) {

                Toast.makeText(applicationContext, "Podaj tytuł", Toast.LENGTH_SHORT).show()
            }

            else {
                save()
                val intent = Intent(this, GamesActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("EXIT", true)
                startActivity(intent)
                finish()
            }
        }
    }
}