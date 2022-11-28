package com.project.roomdbkotlin.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDao {
    @Insert
    fun insert(expense: Expense)

    @Update
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)

    @Query("delete from expense_table")
    fun deleteAllNotes()

    @Query("select * from expense_table")
    fun getAllNotes(): LiveData<List<Expense>>


}