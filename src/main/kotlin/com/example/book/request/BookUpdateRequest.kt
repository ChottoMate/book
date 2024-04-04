package com.example.book.request

data class BookUpdateRequest(val title: String?,
        val authorIdsAdded: List<Int>?,
        val authorIdsRemoved: List<Int>?) {

}