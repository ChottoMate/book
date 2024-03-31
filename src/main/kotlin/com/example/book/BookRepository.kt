package com.example.book

import org.jooq.DSLContext
import org.jooq.Record3
import org.jooq.Result
import org.jooq.generated.book.Book
import org.springframework.stereotype.Repository

@Repository
class BookRepository(private val dslContext: DSLContext) {
    public fun findAll(): Result<Record3<Int, String, String>> {
        val result = dslContext.select(Book.BOOK.BOOKS.BOOK_ID, Book.BOOK.BOOKS.TITLE, Book.BOOK.AUTHORS.NAME)
                .from(Book.BOOK.BOOKS)
                .join(Book.BOOK.BOOKS_AUTHORS).on(Book.BOOK.BOOKS.BOOK_ID.eq(Book.BOOK.BOOKS_AUTHORS.BOOK_ID))
                .join(Book.BOOK.AUTHORS).on(Book.BOOK.AUTHORS.AUTHOR_ID.eq(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID))
                .fetch()

        return result
    }
}