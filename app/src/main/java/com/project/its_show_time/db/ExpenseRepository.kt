package com.project.roomdbkotlin.db

import android.app.Application
import androidx.lifecycle.LiveData
import com.project.roomdbkotlin.utils.subscribeOnBackground

class ExpenseRepository(application: Application) {

    private var noteDao: ExpenseDao
    private var allNotes: LiveData<List<Expense>>

    private val database = NoteDatabase.getInstance(application)

    init {
        noteDao = database.expDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Expense) {
        subscribeOnBackground {
            noteDao.insert(note)
        }
    }

    fun update(note: Expense) {
        subscribeOnBackground {
            noteDao.update(note)
        }
    }

    fun delete(note: Expense) {
        subscribeOnBackground {
            noteDao.delete(note)
        }
    }

    fun deleteAllNotes() {
        subscribeOnBackground {
            noteDao.deleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Expense>> {
        return allNotes
    }

}