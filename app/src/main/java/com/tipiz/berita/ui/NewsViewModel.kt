package com.tipiz.berita.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.berita.data.NewsRepository
import com.tipiz.berita.data.local.entity.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getHeadLineNews() = newsRepository.getHeadLineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkNews()

    fun saveBookmark(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmarkNews(news, true)
        }
    }

    /*
    * fun saveBookmark(news: NewsEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newsRepository.setBookmarkNews(news, true)
            }
        }
    }*/

    fun deleteBookmark(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmarkNews(news, false)
        }
    }


}