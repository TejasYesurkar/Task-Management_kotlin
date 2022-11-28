package com.project.roomdbkotlin.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDao {
    @Insert
    fun insert(note: Expense)

    @Update
    fun update(note: Expense)

    @Delete
    fun delete(note: Expense)

    @Query("delete from expense_table")
    fun deleteAllNotes()

    @Query("select * from expense_table ")
    fun getAllNotes(): LiveData<List<Expense>>


}