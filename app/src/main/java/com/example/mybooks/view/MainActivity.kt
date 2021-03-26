package com.example.mybooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mybooks.adapter.BookAdapter
import com.example.mybooks.db.DBHelper
import com.example.mybooks.model.Book
import com.example.mybooks.view.AddingBookActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var bookList = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToAdd(view: View) {
        val intent = Intent(this, AddingBookActivity::class.java)
        startActivity(intent)
    }

    fun loadData() {
        val database = DBHelper(this)
        bookList = database.getAllBooks()

        val bookAdapter = BookAdapter(this, bookList)
        listView.adapter = bookAdapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}