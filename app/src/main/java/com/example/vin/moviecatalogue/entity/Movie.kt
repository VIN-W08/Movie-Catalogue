package com.example.vin.moviecatalogue.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name="movieId")
    var id: Int?,

    @ColumnInfo(name="poster")
    var poster: String?,

    @ColumnInfo(name="title")
    var title: String?,

    @ColumnInfo(name="description")
    var description: String?,

    @ColumnInfo(name="releaseDate")
    var releaseDate: String?
)
