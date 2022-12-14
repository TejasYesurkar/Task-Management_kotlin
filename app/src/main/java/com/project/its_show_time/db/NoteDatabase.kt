package com.project.roomdbkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.roomdbkotlin.utils.subscribeOnBackground

@Database(entities = [Note::class,Expense::class], version = 6)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun expDao(): ExpenseDao

    companion object {
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): NoteDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, NoteDatabase::class.java,
                    "not_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: NoteDatabase) {
            val noteDao = db.noteDao()
            val expense = db.expDao()
            subscribeOnBackground {
                noteDao.insert(Note("Myself", "Myself", 1,"https://1drv.ms/w/s!AuQ3HZhoo9VRgz41mEvFSDfw0YYP?e=QqW9QT"))
                noteDao.insert(Note("title 2", "desc 2", 2,""))
                noteDao.insert(Note("title 3", "desc 3", 3,""))

                expense.insert(Expense("Credit","11/28/2022", 100,""))
                expense.insert(Expense("Debit","11/28/2022", 10,""))

            }
        }
    }
}