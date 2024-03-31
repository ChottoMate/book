package com.example.book.controller

import com.example.book.model.Book
import com.example.book.service.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.Arrays

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class BookControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @Test
    fun getAllBooks_success() {
        val books = mutableListOf(Book(1, "book1", mutableListOf("Tanaka", "Suzuki")))
        val mapper = ObjectMapper()
        val expectedResponse = mapper.writeValueAsString(books)
        Mockito.`when`(bookService.getAllBooks()).thenReturn(books)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/allBooks"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
    }
}