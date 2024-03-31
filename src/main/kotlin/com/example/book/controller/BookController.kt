package com.example.book.controller

import com.example.book.model.Book
import com.example.book.service.BookService
import org.jooq.Result
import org.jooq.generated.book.tables.records.BooksRecord
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class BookController(private val bookService: BookService) {
    @GetMapping("/allBooks")
    public fun getAllBooks(): List<Book> {
        val result = bookService.getAllBooks()
        return result
    }

}