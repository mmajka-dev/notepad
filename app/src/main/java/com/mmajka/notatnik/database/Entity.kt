package com.mmajka.notatnik.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")

class note (@PrimaryKey(autoGenerate = true) var id: Int = 0,
            @ColumnInfo(name = "title") val title: String?,
            @ColumnInfo(name = "content")val content: String?,
            @ColumnInfo(name = "date")val date: String?,
            @ColumnInfo(name = "isFavorite") val isFavorite: Int,
            @ColumnInfo(name = "isTrash") val isTrash: Int,
            @ColumnInfo(name = "reminderDate")val reminderDate: String?,
            @ColumnInfo(name = "reminderTime")val reminderTime: String?,
            @ColumnInfo(name = "category")val category: String?,
            @ColumnInfo(name = "isSaved")val isSaved: Int
)

