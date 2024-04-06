package com.example.book.service

import com.example.book.repository.BookRepository
import org.jooq.Record
import org.jooq.Record3
import org.jooq.Result
import org.jooq.generated.book.Book
import org.jooq.tools.jdbc.MockResult
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@ExtendWith(MockitoExtension::class)
class BookServiceTest {
    @Mock
    private lateinit var bookRepository: BookRepository

    @InjectMocks
    private lateinit var bookService: BookService

    @Test
    public fun getAllBooks_success() {
        val mockResult = getMockResult()
        Mockito.`when`(bookRepository.findAll()).thenReturn(mockResult as Result<Record3<Int, String, String>>)
        val books = bookService.getAllBooks()
        books.forEach {
            Assertions.assertEquals(1, it.bookId)
            Assertions.assertEquals("book1", it.title)
            Assertions.assertTrue(it.authorName.containsAll(listOf("tanaka")))
        }
        Mockito.verify(bookRepository, Mockito.times(1)).findAll()
    }

    @Test
    public fun getBooksByTitle_success() {
        val mockResult = getMockResult()
        Mockito.`when`(bookRepository.findByBookTitle("book1")).thenReturn(mockResult as Result<Record3<Int, String, String>>)
        val books = bookService.getBooksByTitle("book1")
        books.forEach {
            Assertions.assertEquals(1, it.bookId)
            Assertions.assertEquals("book1", it.title)
            Assertions.assertTrue(it.authorName.containsAll(listOf("tanaka")))
        }
        Mockito.verify(bookRepository, Mockito.times(1)).findByBookTitle("book1")
    }

    @Test
    public fun getBooksByAuthor_success() {
        val mockResult = getMockResult()
        Mockito.`when`(bookRepository.findByAuthor(1)).thenReturn(mockResult as Result<Record3<Int, String, String>>)
        val books = bookService.getBooksByAuthor(1)
        books.forEach {
            Assertions.assertEquals(1, it.bookId)
            Assertions.assertEquals("book1", it.title)
            Assertions.assertTrue(it.authorName.containsAll(listOf("tanaka")))
        }
        Mockito.verify(bookRepository, Mockito.times(1)).findByAuthor(1)
    }

    @Test
    public fun insertBook_success() {
        Mockito.`when`(bookRepository.insertBook("book1")).thenReturn(1)
        Mockito.doNothing().`when`(bookRepository).insertBookAuthor(1, listOf(1, 2))
        bookService.insertBook("book1", listOf(1, 2))
        Mockito.verify(bookRepository, Mockito.times(1)).insertBook("book1")
        Mockito.verify(bookRepository, Mockito.times(1)).insertBookAuthor(1, listOf(1, 2))
    }

    @Test
    public fun updateBook_success() {
        Mockito.doNothing().`when`(bookRepository).updateBookTitle(1, "book2")
        Mockito.doNothing().`when`(bookRepository).addAuthor(1, 3)
        Mockito.doNothing().`when`(bookRepository).addAuthor(1, 4)
        Mockito.doNothing().`when`(bookRepository).removeAuthor(1, 1)
        Mockito.doNothing().`when`(bookRepository).removeAuthor(1, 2)
        bookService.updateBook(1, "book2", listOf(3, 4), listOf(1, 2))
        Mockito.verify(bookRepository, Mockito.times(1)).updateBookTitle(1, "book2")
        Mockito.verify(bookRepository, Mockito.times(1)).addAuthor(1, 3)
        Mockito.verify(bookRepository, Mockito.times(1)).addAuthor(1, 4)
        Mockito.verify(bookRepository, Mockito.times(1)).removeAuthor(1, 1)
        Mockito.verify(bookRepository, Mockito.times(1)).removeAuthor(1, 2)
    }

    @Test
    public fun updateBook_doNothing_noUpdatedArgs() {
        bookService.updateBook(1, null, emptyList(), emptyList())
        Mockito.verify(bookRepository, Mockito.times(0)).updateBookTitle(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())
        Mockito.verify(bookRepository, Mockito.times(0)).addAuthor(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())
        Mockito.verify(bookRepository, Mockito.times(0)).removeAuthor(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())
    }

    private fun getMockResult(): Result<*> {
        val record = Mockito.mock(Record3::class.java)
        Mockito.`when`(record.getValue(Book.BOOK.BOOKS.BOOK_ID)).thenReturn(1)
        Mockito.`when`(record.getValue(Book.BOOK.BOOKS.TITLE)).thenReturn("book1")
        Mockito.`when`(record.getValue(Book.BOOK.AUTHORS.NAME)).thenReturn("tanaka")
        val mockResult = MockResult(record)

        return mockResult.data
    }
}