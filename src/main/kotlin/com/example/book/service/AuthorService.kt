package com.example.book.service

import com.example.book.repository.AuthorRepository
import org.jooq.generated.book.tables.Authors
import org.springframework.stereotype.Service

@Service
class AuthorService(private val repository: AuthorRepository) {
    public fun findAll(): List<Authors> {
        return repository.findAll()
    }

    public fun findByName(name: String): List<Authors> {
        return repository.findByName(name)
    }
}