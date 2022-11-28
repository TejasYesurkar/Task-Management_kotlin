package com.project.roomdbkotlin.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ExpenseViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = ExpenseRepository(app)
    private val allNotes = repository.getAllNotes()

    fun insert(note: Expense) {
        repository.insert(note)
    }

    fun update(note: Expense) {
        repository.update(note)
    }

    fun delete(note: Expense) {
        repository.delete(note)
    }

//    fun deleteAllNotes() {
//        repository.deleteAllNotes()
//    }

    fun getAllNotes(): LiveData<List<Expense>> {
        return allNotes
    }


}