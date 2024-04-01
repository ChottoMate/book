package com.example.book.controller

import com.example.book.model.Book
import com.example.book.request.BookGetRequest
import com.example.book.service.BookService
import org.jooq.Result
import org.jooq.generated.book.tables.records.BooksRecord
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/books")
    public fun getBooks(@RequestParam title: String?): List<Book> {
        val result = bookService.getBooksByTitle(title ?: "")
        return result
    }

    @GetMapping("/books/{authorId}")
    public fun getBooks(@PathVariable authorId: Int): List<Book> {
        val result = bookService.getBooksByAuthor(authorId)
        return result
    }

}