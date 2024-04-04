package com.example.book.repository

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

    public fun findByBookTitle(title: String): Result<Record3<Int, String, String>> {
        val result = dslContext.select(Book.BOOK.BOOKS.BOOK_ID, Book.BOOK.BOOKS.TITLE, Book.BOOK.AUTHORS.NAME)
                .from(Book.BOOK.BOOKS)
                .join(Book.BOOK.BOOKS_AUTHORS).on(Book.BOOK.BOOKS.BOOK_ID.eq(Book.BOOK.BOOKS_AUTHORS.BOOK_ID))
                .join(Book.BOOK.AUTHORS).on(Book.BOOK.AUTHORS.AUTHOR_ID.eq(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID))
                .where(Book.BOOK.BOOKS.TITLE.like("%${title}%"))
                .fetch()

        return result
    }

    public fun findByAuthor(authorId: Int): Result<Record3<Int, String, String>> {
        val result = dslContext.select(Book.BOOK.BOOKS.BOOK_ID, Book.BOOK.BOOKS.TITLE, Book.BOOK.AUTHORS.NAME)
                .from(Book.BOOK.BOOKS)
                .join(Book.BOOK.BOOKS_AUTHORS).on(Book.BOOK.BOOKS.BOOK_ID.eq(Book.BOOK.BOOKS_AUTHORS.BOOK_ID))
                .join(Book.BOOK.AUTHORS).on(Book.BOOK.AUTHORS.AUTHOR_ID.eq(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID))
                .where(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID.eq(authorId))
                .fetch()

        return result
    }

    public fun insertBook(title: String): Int {
        val bookId = dslContext.insertInto(Book.BOOK.BOOKS)
                .set(Book.BOOK.BOOKS.TITLE, title)
                .returningResult(Book.BOOK.BOOKS.BOOK_ID)
                .fetchOne()
                ?.getValue(Book.BOOK.BOOKS.BOOK_ID)
                ?: throw IllegalStateException("Failed to insert book")

        return bookId
    }

    public fun insertBookAuthor(bookId: Int, authorIds: List<Int>) {
        authorIds.forEach{ it ->
            dslContext.insertInto(Book.BOOK.BOOKS_AUTHORS)
                    .set(Book.BOOK.BOOKS_AUTHORS.BOOK_ID, bookId)
                    .set(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID, it)
                    .execute()
        }
    }

    public fun updateBookTitle(bookId: Int, title: String) {
        dslContext.update(Book.BOOK.BOOKS)
                .set(Book.BOOK.BOOKS.TITLE, title)
                .where(Book.BOOK.BOOKS.BOOK_ID.eq(bookId))
                .execute()
    }

    public fun addAuthor(bookId: Int, authorId: Int) {
        dslContext.insertInto(Book.BOOK.BOOKS_AUTHORS)
                .set(Book.BOOK.BOOKS_AUTHORS.BOOK_ID, bookId)
                .set(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID, authorId)
                .execute()
    }

    public fun  removeAuthor(bookId: Int, authorId: Int) {
        dslContext.deleteFrom(Book.BOOK.BOOKS_AUTHORS)
                .where(Book.BOOK.BOOKS_AUTHORS.BOOK_ID.eq(bookId))
                .and(Book.BOOK.BOOKS_AUTHORS.AUTHOR_ID.eq(authorId))
                .execute()
    }
}