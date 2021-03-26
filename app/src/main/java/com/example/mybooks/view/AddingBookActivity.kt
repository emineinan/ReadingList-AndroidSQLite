package com.example.mybooks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mybooks.R
import com.example.mybooks.db.DBHelper
import com.example.mybooks.model.Book
import kotlinx.android.synthetic.main.activity_adding_book.*
import java.lang.Exception

class AddingBookActivity : AppCompatActivity() {
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_book)

        try {
            val bundle: Bundle? = intent.extras
            id = bundle!!.getInt("id", 0)
            if (id != 0) {
                editTextBook.setText(bundle.getString("bookName"))
                editTextAuthor.setText(bundle.getString("author"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addBook(view: View) {
        val db = DBHelper(this)
        val bookName = editTextBook.text.toString()
        val authorName = editTextAuthor.text.toString()

        if (id == 0) {
            if (bookName.isNotEmpty() && authorName.isNotEmpty()) {
                val book = Book(bookName, authorName)
                db.insertBook(book)
                finish()
            } else {
                Toast.makeText(this, "Please fill in the blank fields.", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (bookName.isNotEmpty() && authorName.isNotEmpty()) {
                val book = Book(id, bookName, authorName)
                db.updateBook(book)
                finish()
            } else {
                Toast.makeText(this, "Please fill in the blank fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}