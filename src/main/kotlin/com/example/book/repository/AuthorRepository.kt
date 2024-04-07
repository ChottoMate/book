package com.example.book.repository

import com.example.book.model.AuthorInfo
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Records
import org.jooq.Result
import org.jooq.generated.book.Book
import org.jooq.generated.book.tables.Authors
import org.springframework.stereotype.Repository

@Repository
class AuthorRepository(private val dslContext: DSLContext) {
    fun findAll(): List<AuthorInfo> {
        val result = dslContext.select()
                .from(Book.BOOK.AUTHORS)
                .fetchInto(AuthorInfo::class.java)

        return result
    }

    fun findByName(name: String): List<AuthorInfo> {
        val result = dslContext.select()
                .from(Book.BOOK.AUTHORS)
                .where(Book.BOOK.AUTHORS.NAME.like("%${name}%"))
                .fetchInto(AuthorInfo::class.java)

        return result
    }

    fun insertAuthor(name: String) {
        dslContext.insertInto(Book.BOOK.AUTHORS)
                .set(Book.BOOK.AUTHORS.NAME, name)
                .execute()
    }

    fun update(authorId: Int, name: String) {
        dslContext.update(Book.BOOK.AUTHORS)
                .set(Book.BOOK.AUTHORS.NAME, name)
                .where(Book.BOOK.AUTHORS.AUTHOR_ID.eq(authorId))
                .execute()
    }
}