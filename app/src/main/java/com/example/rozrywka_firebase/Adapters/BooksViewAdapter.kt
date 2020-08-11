package com.example.rozrywka_firebase.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.rozrywka_firebase.DeleteActivities.DeleteBookActivity
import com.example.rozrywka_firebase.EditActivities.EditBookActivity
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.SingleItemActivities.SingleBook
import kotlinx.android.synthetic.main.book_item.view.*


class BooksViewAdapter (private val bookList: MutableList<SingleBook>): RecyclerView.Adapter<BookHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        context = parent.context
        return BookHolder(
            LayoutInflater.from(context).inflate(
                R.layout.book_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = bookList.size

    override fun onBindViewHolder(holder: BookHolder, position: Int) {

        val book = bookList[position]

        val textViewTitle = book.title
        val textViewAuthor = book.author
        val textViewGenre = book.genre
        val textViewYear = book.year
        val textViewRead = book.read

        holder.itemView.bookTitle.setText(textViewTitle)
        holder.itemView.bookAuthor.setText(textViewAuthor)
        holder.itemView.bookGenre.setText(textViewGenre)
        holder.itemView.bookYear.setText(textViewYear)
        holder.itemView.bookRead.setText(textViewRead)


        holder.buttonDelete.setOnClickListener() {

            val intent = Intent(context, DeleteBookActivity::class.java).apply {putExtra("item_id", bookList[position].id)}
            context.startActivity(intent)
        }



        holder.buttonEdit.setOnClickListener() {

            val intent = Intent(context, EditBookActivity::class.java).apply {putExtra("item_id", bookList[position].id)}
            context.startActivity(intent)
        }
    }




}









class BookHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    var buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
    var buttonEdit = itemView.findViewById<Button>(R.id.buttonEdit)
}