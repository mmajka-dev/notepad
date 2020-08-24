package com.mmajka.notatnik.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mmajka.notatnik.database.Repository
import com.mmajka.notatnik.database.note
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class FavoritesViewModel(application: Application): AndroidViewModel(application) {

    private var repository = Repository(application)
    private var allFavorites: Deferred<LiveData<List<note>>> =repository.getAllFavorites()

    fun getFavorites(): LiveData<List<note>> = runBlocking {
        allFavorites.await()
    }
}