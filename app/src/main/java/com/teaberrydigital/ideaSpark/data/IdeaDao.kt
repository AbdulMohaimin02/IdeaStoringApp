package com.teaberrydigital.ideaSpark.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE


@Dao
interface IdeaDao {

    @Insert(onConflict = IGNORE)
    suspend fun addIdea(idea: Idea)

    @Update
    suspend fun updateIdea(idea: Idea)

    @Delete
    suspend fun deleteIdea(idea: Idea)

    @Query("DELETE FROM idea_table")
    suspend fun deleteAllIdeas()


    // The below return a list of all the ideas, wrapped in live data
    @Query("SELECT * FROM idea_table ORDER BY id ASC")
    fun readAllIdeas() : LiveData<List<Idea>>


    @Query("SELECT * FROM idea_table WHERE isFavourite = 1 ORDER BY id ASC")
    fun readAllFavourites() :LiveData<List<Idea>>


}