package com.example.rozrywka_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rozrywka_firebase.Adapters.RecyclerViewAdapter
import com.example.rozrywka_firebase.AddActivities.AddNewBookActivity
import com.example.rozrywka_firebase.AddActivities.AddNewGameActivity
import com.example.rozrywka_firebase.AddActivities.AddNewItemActivity
import com.example.rozrywka_firebase.SingleItemActivities.SingleMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var myAdapter: RecyclerViewAdapter
    private val movieList: MutableList<SingleMovie> = mutableListOf()
    var id: Long = 0
    lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        label_noMovies.isVisible = true

        myAdapter =
            RecyclerViewAdapter(movieList)

        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        moviesRecyclerView.adapter = myAdapter


        auth = FirebaseAuth.getInstance()
        val ref = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid).child("movies")


        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {


                if(p0.exists()) {
                    for(m in p0.children) {

                        val movie = m.getValue(SingleMovie::class.java)
                        movieList.add(movie!!)
                    }

                    myAdapter.notifyDataSetChanged()


                    if(movieList[0].toString() != "null") {
                        label_noMovies.isVisible = false
                    }

                }
            }


        })


    }






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menuItem_addMovie -> {
                val intent = Intent(this, AddNewItemActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menuItem_addBook -> {
                val intent = Intent(this, AddNewBookActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menuItem_addGame -> {
                val intent = Intent(this, AddNewGameActivity::class.java)
                startActivity(intent)
                return true
            }


            R.id.menuItem_showMovies -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("EXIT", true)
                startActivity(intent)
                finish()
                return true
            }
            R.id.menuItem_showBooks -> {
                val intent = Intent(this, BooksActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("EXIT", true)
                startActivity(intent)
                finish()
                return true
            }
            R.id.menuItem_showGames -> {
                val intent = Intent(this, GamesActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("EXIT", true)
                startActivity(intent)
                finish()
                return true
            }


            R.id.menuItem_find -> {
                val intent = Intent(this, FindNewItemActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menuItem_logout -> {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("EXIT", true)
                startActivity(intent)
                finish()
                Toast.makeText(applicationContext, "Wylogowano", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}