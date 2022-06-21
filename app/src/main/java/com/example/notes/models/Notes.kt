package com.example.notes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//1

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "noteText")
    var noteText: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    @ColumnInfo(name = "isPinned")
    var isPinned: Boolean = false

) : Serializable