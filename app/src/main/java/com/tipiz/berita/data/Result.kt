package com.tipiz.berita.data

import kotlin.Result

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : com.tipiz.berita.data.Result<T>()
    data class Error(val error: String) : com.tipiz.berita.data.Result<Nothing>()
    data object Loading : com.tipiz.berita.data.Result<Nothing>()
}