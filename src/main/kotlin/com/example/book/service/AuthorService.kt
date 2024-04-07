package com.example.book.service

import com.example.book.model.AuthorInfo
import com.example.book.repository.AuthorRepository
import org.jooq.generated.book.tables.Authors
import org.springframework.stereotype.Service

@Service
class AuthorService(private val repository: AuthorRepository) {
    fun findAll(): List<AuthorInfo> {
        return repository.findAll()
    }

    fun findByName(name: String): List<AuthorInfo> {
        return repository.findByName(name)
    }

    fun insertAuthor(name: String) {
        return repository.insertAuthor(name)
    }

    fun updateAuthor(authorId: Int, name: String) {
        return repository.update(authorId, name)
    }
}