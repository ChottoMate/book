package com.example.book.service

import com.example.book.BookRepository
import com.example.book.model.Book
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Record3
import org.jooq.Result
import org.jooq.generated.book.Book.BOOK
import org.jooq.generated.book.tables.records.BooksRecord
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {
    public fun getAllBooks(): List<Book> {
        val results = bookRepository.findAll()

        return recordToBook(results)
    }

    private fun recordToBook(bookRecord: Result<Record3<Int, String, String>>): List<Book> {
        var currentBookId = -1
        var currentBookTitle = ""
        val result = mutableListOf<Book>()
        val authors = mutableListOf<String>()
        for (record in bookRecord) {
            val bookId = record.getValue(BOOK.BOOKS.BOOK_ID)
            val title = record.getValue(BOOK.BOOKS.TITLE)
            val authorName = record.getValue(BOOK.AUTHORS.NAME)
            if (currentBookId != bookId) {
                if (currentBookId != -1) {
                    val book = Book(currentBookId, currentBookTitle, authors)
                    result.add(book)
                }
                currentBookId = bookId
                currentBookTitle = title
                authors.clear()
            }
            authors.add(authorName)
        }
        if (currentBookId != -1) {
            result.add(Book(currentBookId, currentBookTitle, authors))
        }

        return result
    }
}