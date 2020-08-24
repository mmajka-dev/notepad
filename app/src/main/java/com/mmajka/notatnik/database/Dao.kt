package com.mmajka.notatnik.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface noteDao {

    @Query("SELECT * FROM note_table WHERE isFavorite = 0 AND isTrash = 0 ")
    fun getAllNotes(): LiveData<List<note>>

    @Query("SELECT * FROM note_table WHERE isFavorite = 1")
    fun getFavorites(): LiveData<List<note>>

    @Query("SELECT * FROM note_table WHERE isTrash = 1")
    fun getAllFromBin(): LiveData<List<note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note: note)

    @Update
    fun updateNote(note: note)

    @Query("DELETE  FROM note_table WHERE isTrash = 'TRUE'")
    fun deleteArchived()

    @Query("UPDATE note_table SET isTrash = 'FALSE' ")
    fun restoreAll()

    @Delete
    fun deleteNote(note: note)

}
