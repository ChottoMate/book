package com.example.book.repository

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Records
import org.jooq.Result
import org.jooq.generated.book.Book
import org.jooq.generated.book.tables.Authors
import org.springframework.stereotype.Repository

@Repository
class AuthorRepository(private val dslContext: DSLContext) {
    public fun findAll(): List<Authors> {
        val result = dslContext.select()
                .from(Book.BOOK.AUTHORS)
                .fetchInto(Authors::class.java)

        return result
    }

    public fun findByName(name: String): List<Authors> {
        val result = dslContext.select()
                .from(Book.BOOK.AUTHORS)
                .where(Book.BOOK.AUTHORS.NAME.like("%name%"))
                .fetchInto(Authors::class.java)

        return result
    }

    public fun insertAuthor(name: String) {
        dslContext.insertInto(Book.BOOK.AUTHORS)
                .set(Book.BOOK.AUTHORS.NAME, name)
                .execute()
    }
}