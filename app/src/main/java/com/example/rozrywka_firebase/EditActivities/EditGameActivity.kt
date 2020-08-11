package com.example.rozrywka_firebase.EditActivities

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
import kotlinx.android.synthetic.main.activity_edit_game.*


class EditGameActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_game)

        val item_id = intent.getStringExtra("item_id")

        lateinit var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val ref = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid).child("games")


        ref.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()) {

                    val itemToEdit = p0.child(item_id)
                    val titleToEdit = itemToEdit.child("title").getValue()
                    val typeToEdit = itemToEdit.child("type").getValue()
                    val yearToEdit = itemToEdit.child("year").getValue()
                    val playedToEdit = itemToEdit.child("played").getValue()

                    if (playedToEdit == "TAK") {
                        editGame_played.isChecked = true
                    }

                    editGame_title.setText(titleToEdit.toString())
                    editGame_type.setText(typeToEdit.toString())
                    editGame_year.setText(yearToEdit.toString())

                }
            }
        })




        editGame_ok.setOnClickListener() {

            val title = editGame_title.text.toString()
            val type = editGame_type.text.toString()
            val year = editGame_year.text.toString()

            var played = "NIE"

            if (editGame_played.isChecked) {
                played = "TAK"
            }

            val game =
                SingleGame(
                    item_id,
                    title,
                    type,
                    year,
                    played
                )

            ref.child(item_id.toString()).setValue(game)

            val intent = Intent(this, GamesActivity::class.java)
            startActivity(intent)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("EXIT", true)
            finish()
            Toast.makeText(applicationContext, "Edytowano", Toast.LENGTH_SHORT).show()
        }

    }
}