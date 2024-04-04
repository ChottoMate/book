package com.example.book.controller

import com.example.book.model.Book
import com.example.book.request.BookInsertRequest
import com.example.book.request.BookUpdateRequest
import com.example.book.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @PostMapping("/book")
    public fun insertBook(@RequestBody request: BookInsertRequest) {
        bookService.insertBook(request.title, request.authorIds)
    }

    @PutMapping("/book/{bookId}")
    public fun updateBook(@PathVariable bookId: Int, @RequestBody request: BookUpdateRequest) {
        bookService.updateBook(bookId, request.title, request.authorIdsAdded, request.authorIdsRemoved)
    }

}