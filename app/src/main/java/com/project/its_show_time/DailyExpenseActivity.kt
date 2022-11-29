package com.project.its_show_time

import android.content.Intent
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.roomdbkotlin.db.ExpenseViewModel
import com.project.roomdbkotlin.db.NoteViewModel

const val ADD_EXPENSE_REQUEST = 1
const val EDIT_EXPENSE_REQUEST = 2
const val DELETE_EXPENSE_REQUEST = 3

class DailyExpenseActivity : AppCompatActivity() {
    private lateinit var vm: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter
    private lateinit var button_add_note: FloatingActionButton
    private lateinit var recycler_view: RecyclerView
//    private var types = resources.getStringArray(R.array.investment)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_expense)

        recycler_view = findViewById(R.id.recyclerView) as RecyclerView
        setUpRecyclerView()

        setUpListeners()
        button_add_note = findViewById(R.id.button_add_note) as FloatingActionButton

        button_add_note.setOnClickListener {
            val intent = Intent(this, AddExpsense::class.java)
            startActivityForResult(intent, ADD_EXPENSE_REQUEST)
        }

        vm = ViewModelProviders.of(this)[ExpenseViewModel::class.java]

        vm.getAllNotes().observe(this, Observer {
            Log.i("Expense observed", "$it")
            adapter.submitList(it)
        })
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = ExpenseAdapter { text, clickedNote ->
            if (text.equals("delete")) {
//                val intent = Intent(this, AddEditTask::class.java)
//                intent.putExtra(EXTRA_ID, clickedNote.id)
//                intent.putExtra(EXTRA_TITLE, clickedNote.title)
//                intent.putExtra(EXTRA_DESCRIPTION, clickedNote.description)
//                intent.putExtra(EXTRA_PRIORITY, clickedNote.priority)
//                startActivityForResult(intent, DELETE_NOTE_REQUEST)

            } else if (text.equals("edit")) {

//                val intent = Intent(this, AddEditTask::class.java)
//                intent.putExtra(EXTRA_ID, clickedNote.id)
//                intent.putExtra(EXTRA_TITLE, clickedNote.title)
//                intent.putExtra(EXTRA_DESCRIPTION, clickedNote.description)
//                intent.putExtra(EXTRA_PRIORITY, clickedNote.priority)
//                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }

        }
        recycler_view.adapter = adapter
    }

    private fun setUpListeners() {
//        button_add_note.setOnClickListener {
//            val intent = Intent(this, AddEditTask::class.java)
//            startActivityForResult(intent, ADD_NOTE_REQUEST)
//        }

        // swipe listener
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, UCharacter.IndicPositionalCategory.LEFT or UCharacter.IndicPositionalCategory.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.getNoteAt(viewHolder.adapterPosition)
                vm.delete(note)
            }

        }).attachToRecyclerView(recycler_view)
    }
}