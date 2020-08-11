package com.example.rozrywka_firebase.AddActivities

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
import kotlinx.android.synthetic.main.activity_add_new_item.*

class AddNewItemActivity : AppCompatActivity () {


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_item)


            val title_exists = intent.getStringExtra("item_title")
            val country_exists = intent.getStringExtra("item_country")
            val network_exists = intent.getStringExtra("item_network")

            var title = ""
            var country = ""
            var network = ""
            var seen = "NIE"

            if(title_exists != "") {
                addItem_title.setText(title_exists)
            }

            if(country_exists != "") {
                addItem_country.setText(country_exists)
            }

            if(network_exists != "") {
                addItem_network.setText(network_exists)
            }


            lateinit var auth: FirebaseAuth
            auth = FirebaseAuth.getInstance()
            val ref = FirebaseDatabase.getInstance().getReference("users")
            var testId: Long = 0

            ref.child(auth.currentUser!!.uid).child("movies").addValueEventListener(object: ValueEventListener {
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

                title = addItem_title.text.toString()
                country = addItem_country.text.toString()
                network = addItem_network.text.toString()


                val movie =
                    SingleMovie(
                        testId.toString(),
                        title,
                        country,
                        network,
                        seen
                    )



                if (testId != null) {
                    ref.child(auth.currentUser!!.uid).child("movies").child(testId.toString()).setValue(movie)
                }

                Toast.makeText(applicationContext, "Dodano", Toast.LENGTH_SHORT).show()
            }



            addItem_seen.setOnClickListener() {

                if(addItem_seen.isChecked)
                {
                    seen = "TAK"
                }

                else
                {
                    seen = "NIE"
                }
            }




            addItem_ok.setOnClickListener() {

                if(addItem_title.text.isNullOrEmpty()) {

                    Toast.makeText(applicationContext, "Podaj tytuł", Toast.LENGTH_SHORT).show()
                }

                else {
                    save()
                    val intent = Intent(this, MainActivity::class.java)
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