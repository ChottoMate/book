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
@RequestMapping("/api/v1/book")
class BookController(private val bookService: BookService) {
    @GetMapping("/all")
    public fun getAllBooks(): List<Book> {
        val result = bookService.getAllBooks()
        return result
    }

    @GetMapping("/title")
    public fun getBooks(@RequestParam title: String?): List<Book> {
        val result = bookService.getBooksByTitle(title ?: "")
        return result
    }

    @GetMapping("/author/{authorId}")
    public fun getBooks(@PathVariable authorId: Int): List<Book> {
        val result = bookService.getBooksByAuthor(authorId)
        return result
    }

    @PostMapping("/")
    public fun insertBook(@RequestBody request: BookInsertRequest) {
        bookService.insertBook(request.title, request.authorIds)
    }

    @PutMapping("/{bookId}")
    public fun updateBook(@PathVariable bookId: Int, @RequestBody request: BookUpdateRequest) {
        bookService.updateBook(bookId, request.title, request.authorIdsAdded, request.authorIdsRemoved)
    }

}