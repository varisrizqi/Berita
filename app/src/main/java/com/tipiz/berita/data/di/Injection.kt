package com.tipiz.berita.data.di

import android.content.Context
import com.tipiz.berita.data.NewsRepository
import com.tipiz.berita.data.local.room.NewsDatabase
import com.tipiz.berita.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.NewsDao()

        return NewsRepository.getInstance(apiService, dao)
    }
}