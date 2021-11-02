package com.teaberrydigital.ideaSpark.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "idea_table")
data class Idea(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val isFavourite: Boolean = false
)