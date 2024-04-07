package com.example.book.repository

import com.example.book.model.AuthorInfo
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
class AuthorRepositoryTest {
    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    @Sql("/book.sql")
    fun findAll_success() {
        val result = authorRepository.findAll()

        val expected = listOf(
                AuthorInfo(1, "author1_1"),
                AuthorInfo(2, "author1_2"),
                AuthorInfo(3, "author1_3"),
                AuthorInfo(4, "author2_1"),
                AuthorInfo(5, "author2_2"),
                AuthorInfo(6, "author2_3"),
                AuthorInfo(7, "author3_1"),
                AuthorInfo(8, "author3_2"),
                AuthorInfo(9, "author3_3"),
        )
        Assertions.assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test
    @Sql("/book.sql")
    fun findByName_success() {
        val result = authorRepository.findByName("author2")

        val expected = listOf(
                AuthorInfo(4, "author2_1"),
                AuthorInfo(5, "author2_2"),
                AuthorInfo(6, "author2_3"),
        )
        Assertions.assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test
    fun insertAuthor_success() {
        authorRepository.insertAuthor("author1_4")

        val authorName = jdbcTemplate.queryForObject<String>("SELECT name FROM `book`.`authors`")
        Assertions.assertEquals(authorName, "author1_4")
    }

    @Test
    @Sql("/book.sql")
    fun update_success() {
        authorRepository.update(1, "author1_4")

        val authorName = jdbcTemplate.queryForObject<String>("SELECT name FROM `book`.`authors` WHERE `author_id` = 1")
        Assertions.assertEquals(authorName, "author1_4")
    }
}