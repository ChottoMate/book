package com.example.book.controller

import com.example.book.model.AuthorInfo
import com.example.book.request.AuthorInsertRequest
import com.example.book.request.AuthorUpdateRequest
import com.example.book.service.AuthorService
import org.jooq.generated.book.tables.Authors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/author")
class AuthorController(private val service: AuthorService) {
    @GetMapping("/all")
    fun getAllAuthors(): List<AuthorInfo> {
        return service.findAll()
    }

    @GetMapping("/{name}")
    fun getAuthorsByName(@PathVariable name: String): List<AuthorInfo> {
        return service.findByName(name)
    }

    @PostMapping("/")
    fun insertAuthor(@RequestBody request: AuthorInsertRequest) {
        service.insertAuthor(request.name)
    }

    @PutMapping("/{authorId}")
    fun updateAuthor(@PathVariable authorId: Int, @RequestBody request: AuthorUpdateRequest) {
        service.updateAuthor(authorId, request.name)
    }
}