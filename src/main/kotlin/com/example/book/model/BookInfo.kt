package com.example.book.model

data class BookInfo(val bookId: Int,
                    val title: String,
                    val authors: List<AuthorInfo>) {
}