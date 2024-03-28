package com.example.book

import org.jooq.DSLContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookApplication

fun main(args: Array<String>) {
	runApplication<BookApplication>(*args)
}
