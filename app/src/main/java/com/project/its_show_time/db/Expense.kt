package com.project.roomdbkotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class Expense(val type: String,
                val date: String,
                val amount: Float,
                val reason: String,
                @PrimaryKey(autoGenerate = false) val id: Int? = null)
