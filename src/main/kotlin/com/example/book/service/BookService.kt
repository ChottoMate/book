package com.example.book.service

import com.example.book.repository.BookRepository
import com.example.book.model.Book
import org.jooq.Record3
import org.jooq.Result
import org.jooq.generated.book.Book.BOOK
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(private val bookRepository: BookRepository) {
    public fun getAllBooks(): List<Book> {
        val results = bookRepository.findAll()

        return recordToBook(results)
    }

    public fun getBooksByTitle(title: String): List<Book> {
        val results = bookRepository.findByBookTitle(title)

        return recordToBook(results)
    }

    public fun getBooksByAuthor(authorId: Int): List<Book> {
        val results = bookRepository.findByAuthor(authorId)

        return recordToBook(results)
    }

    @Transactional
    public fun insertBook(title: String, authorIds: List<Int>) {
        val bookId = bookRepository.insertBook(title)
        bookRepository.insertBookAuthor(bookId, authorIds)
    }

    @Transactional
    public fun updateBook(bookId: Int, title: String?, authorIdsAdded: List<Int>?, authorIdsRemoved: List<Int>?) {
        title?.let { bookRepository.updateBookTitle(bookId, it) }
        authorIdsAdded?.forEach { bookRepository.addAuthor(bookId, it) }
        authorIdsRemoved?.forEach { bookRepository.removeAuthor(bookId, it) }
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