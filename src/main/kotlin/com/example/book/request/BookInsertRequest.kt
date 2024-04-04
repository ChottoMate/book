package com.example.book.request

data class BookInsertRequest(val title: String,
                             val authorIds: List<Int>) {
}