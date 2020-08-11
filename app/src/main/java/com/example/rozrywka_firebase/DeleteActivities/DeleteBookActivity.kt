package com.example.rozrywka_firebase.DeleteActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rozrywka_firebase.BooksActivity
import com.example.rozrywka_firebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_delete_item.*

class DeleteBookActivity: AppCompatActivity () {


    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_item)

        auth = FirebaseAuth.getInstance()

        buttonDeleteYes.setOnClickListener() {

            val id = intent.getStringExtra("item_id")

            FirebaseDatabase.getInstance().reference
                .child("users")
                .child(auth.currentUser!!.uid)
                .child("books")
                .child(id)
                .removeValue()

            val intent = Intent(this, BooksActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("EXIT", true)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "Usunięto", Toast.LENGTH_SHORT).show()
        }


        buttonDeleteNo.setOnClickListener() {

            finish()
        }
    }
}