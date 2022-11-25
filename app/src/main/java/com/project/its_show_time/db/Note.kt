package com.project.roomdbkotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(val title: String,
                val description: String,
                val priority: Int,
                val docLink: String,
                @PrimaryKey(autoGenerate = false) val id: Int? = null)

