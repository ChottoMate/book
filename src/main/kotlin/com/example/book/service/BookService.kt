package com.example.book.service

import com.example.book.repository.BookRepository
import com.example.book.model.BookInfo
import org.jooq.Record3
import org.jooq.Result
import org.jooq.generated.book.Book.BOOK
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(private val bookRepository: BookRepository) {
    fun getAllBooks(): List<BookInfo> {
        val results = bookRepository.findAll()

        return results
    }

    fun getBooksByTitle(title: String): List<BookInfo> {
        val results = bookRepository.findByBookTitle(title)

        return results
    }

    fun getBooksByAuthor(authorId: Int): List<BookInfo> {
        val results = bookRepository.findByAuthor(authorId)

        return results
    }

    @Transactional
    fun insertBook(title: String, authorIds: List<Int>) {
        val bookId = bookRepository.insertBook(title)
        bookRepository.insertBookAuthor(bookId, authorIds)
    }

    @Transactional
    fun updateBook(bookId: Int, title: String?, authorIdsAdded: List<Int>?, authorIdsRemoved: List<Int>?) {
        title?.let { bookRepository.updateBookTitle(bookId, it) }
        authorIdsAdded?.forEach { bookRepository.addAuthor(bookId, it) }
        authorIdsRemoved?.forEach { bookRepository.removeAuthor(bookId, it) }
    }

}