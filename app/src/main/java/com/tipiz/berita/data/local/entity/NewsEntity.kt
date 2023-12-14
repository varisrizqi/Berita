package com.tipiz.berita.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
class NewsEntity(

    @field:ColumnInfo("title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo("publishedAt")
    val publishedAt:String,

    @field:ColumnInfo("urlToImage")
    val urlToImage:String? = null,

    @field:ColumnInfo("url")
    val url:String? = null,

    @field:ColumnInfo("bookmarked")
    var isBookmarked:Boolean
    )