package com.example.book.repository

import com.example.book.model.BookInfo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class BookRepositoryTest {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    @Sql("/book.sql")
    fun findAll_success() {
        val result = bookRepository.findAll()

        val expected = listOf(
                BookInfo(1, "book1", listOf("author1_1", "author1_2", "author1_3")),
                BookInfo(2, "book2", listOf("author2_1", "author2_2", "author2_3")),
                BookInfo(3, "book3", listOf("author3_1", "author3_2", "author3_3")),
                BookInfo(4, "book4", listOf("author2_1"))
        )
        Assertions.assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test
    @Sql("/book.sql")
    fun findByBookTitle_success() {
        val result = bookRepository.findByBookTitle("book2")

        val expected = listOf(
                BookInfo(2, "book2", listOf("author2_1", "author2_2", "author2_3")),
        )
        Assertions.assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test
    @Sql("/book.sql")
    fun findByAuthor_success() {
        val result = bookRepository.findByAuthor(4)

        val expected = listOf(
                BookInfo(2, "book2", listOf("author2_1", "author2_2", "author2_3")),
                BookInfo(4, "book4", listOf("author2_1")),
        )
        Assertions.assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test
    fun insertBook_success() {
        val bookId = bookRepository.insertBook("book5")

        val insertedBook = jdbcTemplate.queryForObject<String>("SELECT title FROM `book`.`books` WHERE `book_id` = " + bookId)
        Assertions.assertEquals(insertedBook, "book5")
    }

    @Test
    @Sql("/book.sql")
    fun insertBookAuthor_success() {
        bookRepository.insertBookAuthor(1, listOf(4, 5))

        val updatedAuthorIds = jdbcTemplate.queryForList("SELECT author_id FROM `book`.`books_authors` WHERE `book_id` = 1", Int::class.java)
        Assertions.assertTrue(updatedAuthorIds.containsAll(arrayListOf(1, 2, 3, 4, 5)))
    }

    @Test
    @Sql("/book.sql")
    fun updateBookTitle_success() {
        bookRepository.updateBookTitle(1, "book11")

        val updatedTitle = jdbcTemplate.queryForObject<String>("SELECT title FROM `book`.`books` WHERE `book_id` = 1")
        Assertions.assertEquals("book11", updatedTitle)
    }

    @Test
    @Sql("/book.sql")
    fun addAuthor_success() {
        bookRepository.addAuthor(1, 4)

        val addedAuthorIds = jdbcTemplate.queryForList("SELECT author_id FROM `book`.`books_authors` WHERE `book_id` = 1", Int::class.java)
        Assertions.assertTrue(addedAuthorIds.containsAll(arrayListOf(1, 2, 3, 4)))
    }

    @Test
    @Sql("/book.sql")
    fun removeAuthor_success() {
        bookRepository.removeAuthor(1, 3)

        val removedAuthorIds = jdbcTemplate.queryForList("SELECT author_id FROM `book`.`books_authors` WHERE `book_id` = 1", Int::class.java)
        Assertions.assertTrue(removedAuthorIds.containsAll(arrayListOf(1, 2)))
    }
}