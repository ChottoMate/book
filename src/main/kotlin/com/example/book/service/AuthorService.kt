package com.example.book.service

import com.example.book.model.AuthorInfo
import com.example.book.repository.AuthorRepository
import org.jooq.generated.book.tables.Authors
import org.springframework.stereotype.Service

@Service
class AuthorService(private val repository: AuthorRepository) {
    public fun findAll(): List<AuthorInfo> {
        return repository.findAll()
    }

    public fun findByName(name: String): List<AuthorInfo> {
        return repository.findByName(name)
    }

    public fun insertAuthor(name: String) {
        return repository.insertAuthor(name)
    }

    public fun updateAuthor(authorId: Int, name: String) {
        return repository.update(authorId, name)
    }
}