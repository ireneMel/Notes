package com.example.notes.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//1

@Entity(tableName = "notes")
class Notes : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0

    @ColumnInfo(name = "title")
    var title:String = ""

    @ColumnInfo(name = "noteText")
    var noteText:String = ""

    @ColumnInfo(name = "date")
    var date:String=""

    @ColumnInfo(name = "isPinned")
    var isPinned = false

}