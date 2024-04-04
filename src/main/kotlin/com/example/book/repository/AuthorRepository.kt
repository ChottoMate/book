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
}