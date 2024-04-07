package com.example.book.service

import com.example.book.model.AuthorInfo
import com.example.book.model.BookInfo
import com.example.book.repository.AuthorRepository
import com.example.book.repository.BookRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AuthorServiceTest {
    @Mock
    private lateinit var authorRepository: AuthorRepository

    @InjectMocks
    private lateinit var authorService: AuthorService

    @Test
    fun getAllBooks_success() {
        val authorsMock = listOf(AuthorInfo(1, "author1"))
        Mockito.`when`(authorRepository.findAll()).thenReturn(authorsMock)

        val authors = authorService.findAll()

        authors.forEach {
            Assertions.assertEquals(1, it.authorId)
            Assertions.assertEquals("author1", it.name)
        }
        Mockito.verify(authorRepository, Mockito.times(1)).findAll()
    }

    @Test
    fun findByName_success() {
        val authorsMock = listOf(AuthorInfo(1, "author1"))
        Mockito.`when`(authorRepository.findByName("author1")).thenReturn(authorsMock)

        val authors = authorService.findByName("author1")

        authors.forEach {
            Assertions.assertEquals(1, it.authorId)
            Assertions.assertEquals("author1", it.name)
        }
        Mockito.verify(authorRepository, Mockito.times(1)).findByName("author1")
    }

    @Test
    fun insertAuthor_success() {
        Mockito.doNothing().`when`(authorRepository).insertAuthor("author1")

        authorService.insertAuthor("author1")

        Mockito.verify(authorRepository, Mockito.times(1)).insertAuthor("author1")
    }

    @Test
    fun updateAuthor_success() {
        Mockito.doNothing().`when`(authorRepository).update(1, "author2")

        authorService.updateAuthor( 1,"author2")

        Mockito.verify(authorRepository, Mockito.times(1)).update(1, "author2")
    }
}