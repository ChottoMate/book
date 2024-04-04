package com.example.book.controller

import com.example.book.service.AuthorService
import org.jooq.generated.book.tables.Authors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/author")
class AuthorController(private val service: AuthorService) {
    @GetMapping("/all")
    fun getAllAuthors(): List<Authors> {
        return service.findAll()
    }
}