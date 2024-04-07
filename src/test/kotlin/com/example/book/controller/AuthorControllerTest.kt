package com.example.book.controller

import com.example.book.model.AuthorInfo
import com.example.book.service.AuthorService
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
class AuthorControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authorService: AuthorService

    @Test
    fun getAllAuthors_success() {
        val authors = listOf(AuthorInfo(1, "author1"))
        val mapper = ObjectMapper()
        val expectedResponse = mapper.writeValueAsString(authors)
        Mockito.`when`(authorService.findAll()).thenReturn(authors)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/author/all"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
        Mockito.verify(authorService, Mockito.times(1)).findAll()
    }

    @Test
    fun getAuthorsByName_success() {
        val authors = listOf(AuthorInfo(1, "author1"))
        val mapper = ObjectMapper()
        val expectedResponse = mapper.writeValueAsString(authors)
        Mockito.`when`(authorService.findByName("author1")).thenReturn(authors)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/author/author1"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
        Mockito.verify(authorService, Mockito.times(1)).findByName("author1")
    }

    @Test
    fun insertAuthor_success() {
        val request = """
            {
                "name": "author1"
            }
        """.trimIndent()
        Mockito.doNothing().`when`(authorService).insertAuthor("author1")

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/author/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk)
        Mockito.verify(authorService, Mockito.times(1)).insertAuthor("author1")
    }

    @Test
    fun updateAuthor_success() {
        val request = """
            {
                "name": "author2"
            }
        """.trimIndent()
        Mockito.doNothing().`when`(authorService).updateAuthor(1, "author2")

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/author/1").content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
        Mockito.verify(authorService, Mockito.times(1)).updateAuthor(1, "author2")
    }
}