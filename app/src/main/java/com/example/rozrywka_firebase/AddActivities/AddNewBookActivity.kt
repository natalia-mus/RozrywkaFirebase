package com.example.rozrywka_firebase.AddActivities

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
import kotlinx.android.synthetic.main.activity_add_new_book.*

class AddNewBookActivity : AppCompatActivity () {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_book)


        val title_exists = intent.getStringExtra("book_title")
        val author_exists = intent.getStringExtra("book_author")
        val genre_exists = intent.getStringExtra("book_genre")
        val year_exists = intent.getStringExtra("book_year")

        var title = ""
        var author = ""
        var genre = ""
        var year = ""
        var read = "NIE"


        if(title_exists != "") {
            addBook_title.setText(title_exists)
        }

        if(author_exists != "") {
            addBook_author.setText(author_exists)
        }

        if(genre_exists != "") {
            addBook_genre.setText(genre_exists)
        }

        if(year_exists != "") {
            addBook_year.setText(year_exists)
        }



        lateinit var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val ref = FirebaseDatabase.getInstance().getReference("users")
        var testId: Long = 0

        ref.child(auth.currentUser!!.uid).child("books").addValueEventListener(object: ValueEventListener {
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

            title = addBook_title.text.toString()
            author = addBook_author.text.toString()
            genre = addBook_genre.text.toString()
            year = addBook_year.text.toString()


            val book =
                SingleBook(
                    testId.toString(),
                    title,
                    author,
                    genre,
                    year,
                    read
                )




            if (testId != null) {
                ref.child(auth.currentUser!!.uid).child("books").child(testId.toString()).setValue(book)
            }

            Toast.makeText(applicationContext, "Dodano", Toast.LENGTH_SHORT).show()
        }



        addBook_read.setOnClickListener() {

            if(addBook_read.isChecked)
            {
                read = "TAK"
            }

            else
            {
                read = "NIE"
            }
        }




        addBook_ok.setOnClickListener() {

            if(addBook_title.text.isNullOrEmpty()) {

                Toast.makeText(applicationContext, "Podaj tytuł", Toast.LENGTH_SHORT).show()
            }

            else {
                save()
                val intent = Intent(this, BooksActivity::class.java)
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