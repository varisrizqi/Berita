package com.tipiz.berita.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tipiz.berita.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("select * from news order by publishedAt desc")
    fun getNews(): LiveData<List<NewsEntity>>

    @Query("select * from news where bookmarked = 1")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news:List<NewsEntity>)

    @Update
    suspend fun updateNews(news:NewsEntity)

    @Query("delete from news where bookmarked = 0")
    suspend fun deleteAll()

    @Query("select exists(select * from news where title = :title and bookmarked = 1)")
    suspend fun isNewsBookmarked(title:String): Boolean

}