package com.example.rozrywka_firebase.EditActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rozrywka_firebase.BooksActivity
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.SingleItemActivities.SingleBook
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_book.*

class EditBookActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        val item_id = intent.getStringExtra("item_id")

        lateinit var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val ref = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid).child("books")


        ref.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()) {

                    val itemToEdit = p0.child(item_id)
                    val titleToEdit = itemToEdit.child("title").getValue()
                    val authorToEdit = itemToEdit.child("author").getValue()
                    val genreToEdit = itemToEdit.child("genre").getValue()
                    val yearToEdit = itemToEdit.child("year").getValue()
                    val readToEdit = itemToEdit.child("read").getValue()

                    if (readToEdit == "TAK") {
                        editBook_read.isChecked = true
                    }

                    editBook_title.setText(titleToEdit.toString())
                    editBook_author.setText(authorToEdit.toString())
                    editBook_genre.setText(genreToEdit.toString())
                    editBook_year.setText(yearToEdit.toString())

                }
            }
        })




        editBook_ok.setOnClickListener() {

            val title = editBook_title.text.toString()
            val author = editBook_author.text.toString()
            val genre = editBook_genre.text.toString()
            val year = editBook_year.text.toString()

            var read = "NIE"

            if (editBook_read.isChecked) {
                read = "TAK"
            }

            val book =
                SingleBook(
                    item_id,
                    title,
                    author,
                    genre,
                    year,
                    read
                )

            ref.child(item_id.toString()).setValue(book)

            val intent = Intent(this, BooksActivity::class.java)
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