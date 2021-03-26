package com.example.mybooks.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.mybooks.model.Book

val database_name = "MyDatabase"
val table_name = "Books"
val col_book = "book"
val col_author = "author"
val col_id = "id"

class DBHelper(var context: Context) : SQLiteOpenHelper(context, database_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = " CREATE TABLE " + table_name + "(" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_book + " TEXT," +
                col_author + " TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + table_name)
        onCreate(db)
    }

    fun insertBook(book: Book) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(col_book, book.bookName)
        values.put(col_author, book.authorName)
        db.insert(table_name, null, values)
        db.close()
    }

    fun getAllBooks(): ArrayList<Book> {
        val list = ArrayList<Book>()
        val db = this.readableDatabase
        val query = "SELECT * FROM " + table_name
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val book = Book()
            book.id = cursor.getInt(cursor.getColumnIndex(col_id))
            book.bookName = cursor.getString(cursor.getColumnIndex(col_book))
            book.authorName = cursor.getString(cursor.getColumnIndex(col_author))
            list.add(book)
        }
        db.close()
        return list
    }

    fun updateBook(book: Book) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(col_book, book.bookName)
        values.put(col_author, book.authorName)
        db.update(table_name, values, "id=?", arrayOf(book.id.toString()))
        db.close()
    }

    fun deleteBook(id: Int) {
        val db = this.writableDatabase
        db.delete(table_name, "id=?", arrayOf(id.toString()))
        db.close()
    }

}