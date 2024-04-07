package com.example.book.repository

import com.example.book.model.BookInfo
import org.jooq.DSLContext
import org.jooq.Record3
import org.jooq.Result
import org.jooq.generated.book.Book
import org.springframework.stereotype.Repository

@Repository
class BookRepository(private val dslContext: DSLContext) {
    fun findAll(): List<BookInfo> {
        val result = dslContext.select(Book.BOOK.BOOKS.BOOK_ID, Book.BOOK.BOOKS.TITLE, Book.BOOK.AUTHORS.NAME)
                .from(Book.BOOK.BOOKS)
                .join(Book.BOOK.BOOKS_AUTHORS).on(Book.BOOK.BOOKS.BOOK_ID.eq(Book.BOOK.BOOKS_AUTHORS.BOOK_ID))
                .join(Book.BOOK.AUTHORS).on(Book.BOOK.AUTHORS.AUTHOR_ID.eq(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID))
                .fetch()

        return recordToBook(result)
    }

    fun findByBookTitle(title: String): List<BookInfo> {
        val result = dslContext.select(Book.BOOK.BOOKS.BOOK_ID, Book.BOOK.BOOKS.TITLE, Book.BOOK.AUTHORS.NAME)
                .from(Book.BOOK.BOOKS)
                .join(Book.BOOK.BOOKS_AUTHORS).on(Book.BOOK.BOOKS.BOOK_ID.eq(Book.BOOK.BOOKS_AUTHORS.BOOK_ID))
                .join(Book.BOOK.AUTHORS).on(Book.BOOK.AUTHORS.AUTHOR_ID.eq(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID))
                .where(Book.BOOK.BOOKS.TITLE.like("%${title}%"))
                .fetch()

        return recordToBook(result)
    }

    fun findByAuthor(authorId: Int): List<BookInfo> {
        val subQuery = dslContext.select(Book.BOOK.BOOKS.BOOK_ID)
                .from(Book.BOOK.BOOKS)
                .join(Book.BOOK.BOOKS_AUTHORS).on(Book.BOOK.BOOKS.BOOK_ID.eq(Book.BOOK.BOOKS_AUTHORS.BOOK_ID))
                .where(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID.eq(authorId))
        val result = dslContext.select(Book.BOOK.BOOKS.BOOK_ID, Book.BOOK.BOOKS.TITLE, Book.BOOK.AUTHORS.NAME)
                .from(Book.BOOK.BOOKS)
                .join(Book.BOOK.BOOKS_AUTHORS).on(Book.BOOK.BOOKS.BOOK_ID.eq(Book.BOOK.BOOKS_AUTHORS.BOOK_ID))
                .join(Book.BOOK.AUTHORS).on(Book.BOOK.AUTHORS.AUTHOR_ID.eq(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID))
                .where(Book.BOOK.BOOKS.BOOK_ID.`in`(subQuery))
                .fetch()

        return recordToBook(result)
    }

    fun insertBook(title: String): Int {
        val bookId = dslContext.insertInto(Book.BOOK.BOOKS)
                .set(Book.BOOK.BOOKS.TITLE, title)
                .returningResult(Book.BOOK.BOOKS.BOOK_ID)
                .fetchOne()
                ?.getValue(Book.BOOK.BOOKS.BOOK_ID)
                ?: throw IllegalStateException("Failed to insert book")

        return bookId
    }

    fun insertBookAuthor(bookId: Int, authorIds: List<Int>) {
        authorIds.forEach{ it ->
            dslContext.insertInto(Book.BOOK.BOOKS_AUTHORS)
                    .set(Book.BOOK.BOOKS_AUTHORS.BOOK_ID, bookId)
                    .set(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID, it)
                    .execute()
        }
    }

    fun updateBookTitle(bookId: Int, title: String) {
        dslContext.update(Book.BOOK.BOOKS)
                .set(Book.BOOK.BOOKS.TITLE, title)
                .where(Book.BOOK.BOOKS.BOOK_ID.eq(bookId))
                .execute()
    }

    fun addAuthor(bookId: Int, authorId: Int) {
        dslContext.insertInto(Book.BOOK.BOOKS_AUTHORS)
                .set(Book.BOOK.BOOKS_AUTHORS.BOOK_ID, bookId)
                .set(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID, authorId)
                .execute()
    }

    fun  removeAuthor(bookId: Int, authorId: Int) {
        dslContext.deleteFrom(Book.BOOK.BOOKS_AUTHORS)
                .where(Book.BOOK.BOOKS_AUTHORS.BOOK_ID.eq(bookId))
                .and(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID.eq(authorId))
                .execute()
    }

    private fun recordToBook(bookRecord: Result<Record3<Int, String, String>>): List<BookInfo> {
        var currentBookId = -1
        var currentBookTitle = ""
        val result = mutableListOf<BookInfo>()
        var authors = mutableListOf<String>()
        val sortedBookRecord = bookRecord.sortedBy { it.getValue(Book.BOOK.BOOKS.BOOK_ID) }
        for (record in sortedBookRecord) {
            val bookId = record.getValue(Book.BOOK.BOOKS.BOOK_ID)
            val title = record.getValue(Book.BOOK.BOOKS.TITLE)
            val authorName = record.getValue(Book.BOOK.AUTHORS.NAME)
            if (currentBookId != bookId) {
                if (currentBookId != -1) {
                    val bookInfo = BookInfo(currentBookId, currentBookTitle, authors)
                    result.add(bookInfo)
                }
                currentBookId = bookId
                currentBookTitle = title
                authors = arrayListOf()
            }
            authors.add(authorName)
        }
        if (currentBookId != -1) {
            result.add(BookInfo(currentBookId, currentBookTitle, authors))
        }

        return result
    }
}