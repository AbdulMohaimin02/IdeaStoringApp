package com.teaberrydigital.ideaSpark.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Idea :: class], version = 1, exportSchema = false)
abstract class IdeaDatabase:RoomDatabase() {

    abstract fun ideaDao(): IdeaDao


    // Bunch of boiler plate
    companion object {
        @Volatile
        private var INSTANCE: IdeaDatabase? = null

        fun getDatabase(context: Context): IdeaDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IdeaDatabase::class.java,
                    "idea_database"
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }

}
