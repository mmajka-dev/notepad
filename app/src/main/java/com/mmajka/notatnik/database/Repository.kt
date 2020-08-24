package com.mmajka.notatnik.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class Repository(application: Application) {

    private var noteDao: noteDao

    init {
        val database: database? = database.getInstance(application.applicationContext)
        noteDao = database!!.dao()
    }

    fun getAllNotes(): Deferred<LiveData<List<note>>> = CoroutineScope(Dispatchers.IO).async {
        noteDao.getAllNotes()
    }

    fun getAllFavorites(): Deferred<LiveData<List<note>>> = CoroutineScope(Dispatchers.IO).async {
        noteDao.getFavorites()
    }

    fun getAllFromBin(): Deferred<LiveData<List<note>>> = CoroutineScope(Dispatchers.IO).async {
        noteDao.getAllFromBin()
    }

    fun insertNote(note: note) = CoroutineScope(Dispatchers.IO).launch {
        noteDao.insertNote(note)
        Log.d("INSERT TEST", "REPOSITORY RUNNING")
    }

    fun updateNote(note: note) = CoroutineScope(Dispatchers.IO).launch {
        noteDao.updateNote(note)
    }

    fun deleteNote(note: note) = CoroutineScope(Dispatchers.IO).launch {
        noteDao.deleteNote(note)
    }

    fun cleanRecyclerBin() = CoroutineScope(Dispatchers.IO).launch {
        noteDao.deleteArchived()
    }

    fun restoreAll() = CoroutineScope(Dispatchers.IO).launch {
        noteDao.restoreAll()
    }
}