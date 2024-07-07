package com.example.book.controller

import com.example.book.model.AuthorInfo
import com.example.book.model.BookInfo
import com.example.book.service.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

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
        val bookInfos = listOf(BookInfo(1, "book1", listOf(AuthorInfo(1, "Tanaka"), AuthorInfo(2, "Suzuki"))))
        val mapper = ObjectMapper()
        val expectedResponse = mapper.writeValueAsString(bookInfos)
        Mockito.`when`(bookService.getAllBooks()).thenReturn(bookInfos)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/all"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
        Mockito.verify(bookService, Mockito.times(1)).getAllBooks()
    }

    @Test
    fun getBooksByTitle_success() {
        val bookInfos = listOf(BookInfo(1, "book1", listOf(AuthorInfo(1, "Tanaka"), AuthorInfo(2, "Suzuki"))))
        val mapper = ObjectMapper()
        val expectedResponse = mapper.writeValueAsString(bookInfos)
        Mockito.`when`(bookService.getBooksByTitle("book1")).thenReturn(bookInfos)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/title").param("title", "book1"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
        Mockito.verify(bookService, Mockito.times(1)).getBooksByTitle("book1")
    }

    @Test
    fun getBooksByAuthorId_success() {
        val bookInfos = listOf(BookInfo(1, "book1", listOf(AuthorInfo(1, "Tanaka"), AuthorInfo(2, "Suzuki"))))
        val mapper = ObjectMapper()
        val expectedResponse = mapper.writeValueAsString(bookInfos)
        Mockito.`when`(bookService.getBooksByAuthor(1)).thenReturn(bookInfos)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/author/1"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
        Mockito.verify(bookService, Mockito.times(1)).getBooksByAuthor(1)
    }

    @Test
    fun insertBook_success() {
        val request = """
            {
                "title": "book1",
                "authorIds": [1, 2]
            }
        """.trimIndent()
        Mockito.doNothing().`when`(bookService).insertBook("book1", listOf(1, 2))

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk)
        Mockito.verify(bookService, Mockito.times(1)).insertBook("book1", listOf(1, 2))
    }

    @Test
    fun updateBook_success() {
        val request = """
            {
                "title": "book1",
                "authorIdsAdded": [1, 2],
                "authorIdsRemoved": [3, 4]
            }
        """.trimIndent()
        Mockito.doNothing().`when`(bookService).updateBook(1, "book1", listOf(1, 2), listOf(3, 4))

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk)
        Mockito.verify(bookService, Mockito.times(1)).updateBook(1, "book1", listOf(1, 2), listOf(3, 4))
    }
}