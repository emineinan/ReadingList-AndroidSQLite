package com.example.mybooks.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat.startActivity
import com.example.mybooks.R
import com.example.mybooks.db.DBHelper
import com.example.mybooks.model.Book
import com.example.mybooks.view.AddingBookActivity
import kotlinx.android.synthetic.main.row_list_item.view.*

class BookAdapter(var context: Context, var bookList: ArrayList<Book>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View
        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.row_list_item, parent, false)
        } else {
            view = convertView
        }

        val myBook = bookList[position]
        view.textViewBook.text = myBook.bookName
        view.textViewAuthor.text = myBook.authorName

        view.imageViewUpdate.setOnClickListener {
            goToUpdate(myBook)
            notifyDataSetChanged()
        }

        view.imageViewDelete.setOnClickListener {
            val database = DBHelper(context)
            database.deleteBook(myBook.id)
            bookList = database.getAllBooks()
            notifyDataSetChanged()
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return bookList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return bookList.size
    }

    fun goToUpdate(book: Book) {
        val intent = Intent(context, AddingBookActivity::class.java)

        intent.putExtra("id", book.id)
        intent.putExtra("bookName", book.bookName)
        intent.putExtra("author", book.authorName)
        startActivity(context, intent, null)
    }
}