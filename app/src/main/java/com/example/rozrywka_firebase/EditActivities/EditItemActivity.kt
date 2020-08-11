package com.example.rozrywka_firebase.EditActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rozrywka_firebase.MainActivity
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.SingleItemActivities.SingleMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_item.*

class EditItemActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        val item_id = intent.getStringExtra("item_id")

        lateinit var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val ref = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid).child("movies")


        ref.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()) {

                    val itemToEdit = p0.child(item_id)
                    val titleToEdit = itemToEdit.child("title").getValue()
                    val countryToEdit = itemToEdit.child("country").getValue()
                    val networkToEdit = itemToEdit.child("network").getValue()
                    val seenToEdit = itemToEdit.child("seen").getValue()

                    if (seenToEdit == "TAK") {
                        editItem_seen.isChecked = true
                    }

                    editItem_title.setText(titleToEdit.toString())
                    editItem_country.setText(countryToEdit.toString())
                    editItem_network.setText(networkToEdit.toString())

                }
            }
        })



        editItem_ok.setOnClickListener() {

            val title = editItem_title.text.toString()
            val country = editItem_country.text.toString()
            val network = editItem_network.text.toString()

            var seen = "NIE"

            if (editItem_seen.isChecked) {
                seen = "TAK"
            }

            val movie =
                SingleMovie(
                    item_id,
                    title,
                    country,
                    network,
                    seen
                )

            ref.child(item_id.toString()).setValue(movie)

            val intent = Intent(this, MainActivity::class.java)
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