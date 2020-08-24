package com.mmajka.notatnik.bin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mmajka.notatnik.database.Repository
import com.mmajka.notatnik.database.note
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class BinViewModel(application: Application): AndroidViewModel(application) {

    private var repository = Repository(application)
    private var allTrashes: Deferred<LiveData<List<note>>> =repository.getAllFromBin()

    fun getAllFromBin(): LiveData<List<note>> = runBlocking {
        allTrashes.await()
    }
}