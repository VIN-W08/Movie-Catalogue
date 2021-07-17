package com.example.vin.moviecatalogue.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vin.moviecatalogue.data.remote.response.GenresItem

@Entity(tableName = "TVShow")
data class TVShow(

    @PrimaryKey
    @ColumnInfo(name="tvShowId")
    var id: Int?,

    @ColumnInfo(name="poster")
    var poster: String?,

    @ColumnInfo(name="title")
    var title: String?,

    @ColumnInfo(name="description")
    var description: String?,

    @ColumnInfo(name="releaseDate")
    var releaseDate: String?,
)
