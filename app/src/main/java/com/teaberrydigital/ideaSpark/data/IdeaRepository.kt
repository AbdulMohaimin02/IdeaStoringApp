package com.teaberrydigital.ideaSpark.data

import androidx.lifecycle.LiveData


// Abstracts the access to multiple data sources
class IdeaRepository(private val ideaDao: IdeaDao) {

    // We are going to add some methods that access the commands from the dao


    // Access all the ideas and get them back as a Live Data list of type Idea entity (class)
    val readAllIdeas: LiveData<List<Idea>> = ideaDao.readAllIdeas()


    // Read all favourite ideas
    val readAllFavourites : LiveData<List<Idea>> = ideaDao.readAllFavourites()

    // Adding ideas function done on a separate thread

    suspend fun addIdea(idea:Idea){
        ideaDao.addIdea(idea)
    }

    suspend fun updateIdea(idea: Idea){
        ideaDao.updateIdea(idea)
    }

    suspend fun deleteIdea(idea: Idea){
        ideaDao.deleteIdea(idea)
    }

    suspend fun deleteAllIdeas(){
        ideaDao.deleteAllIdeas()
    }


}