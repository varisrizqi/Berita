package com.tipiz.berita.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tipiz.berita.BuildConfig
import com.tipiz.berita.data.local.entity.NewsEntity
import com.tipiz.berita.data.local.room.NewsDao
import com.tipiz.berita.data.remote.retrofit.ApiService

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {
    companion object {
        @Volatile

        private var instance: NewsRepository? = null

        fun getInstance(apiService: ApiService, newsDao: NewsDao): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao).also { instance = it }
            }
    }


    fun getHeadLineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            val article = response.articles
            val newsList = article.map { articles ->
                val isBookmarked = newsDao.isNewsBookmarked(articles.title)
                NewsEntity(
                    articles.title,
                    articles.publishedAt,
                    articles.urlToImage,
                    articles.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadLineNews ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getBookmarkNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    suspend fun setBookmarkNews(news: NewsEntity, bookmarkState: Boolean) {
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }
}