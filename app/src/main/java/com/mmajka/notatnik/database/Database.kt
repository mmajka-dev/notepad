package com.mmajka.notatnik.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(note::class), version = 5, exportSchema = false)
abstract class database: RoomDatabase(){

    abstract fun dao(): noteDao

    companion object{
        private var instance: database? = null

        fun getInstance(context: Context): database?{

            if(instance == null){
                instance = Room.databaseBuilder(context, database::class.java, "note_table").fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
    fun deleteInstance(){
        instance == null
    }
}