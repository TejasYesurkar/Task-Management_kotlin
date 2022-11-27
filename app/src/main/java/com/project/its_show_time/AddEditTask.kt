package com.project.its_show_time

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.project.roomdbkotlin.db.Note
import com.project.roomdbkotlin.db.NoteViewModel

const val EXTRA_ID = " com.huawei.todolist.EXTRA_ID"
const val EXTRA_TITLE = " com.huawei.todolist.EXTRA_TITLE"
const val EXTRA_DESCRIPTION = " com.huawei.todolist.EXTRA_DESCRIPTION"
const val EXTRA_LINK = " com.huawei.todolist.EXTRA_LINK"
const val EXTRA_PRIORITY = " com.huawei.todolist.EXTRA_PRIORITY"

class AddEditTask : AppCompatActivity() {
    private lateinit var mode: Mode
    private var noteId: Int = -1
    private lateinit var number_picker_prior: NumberPicker
    private lateinit var et_title: EditText
    private lateinit var et_desc: EditText
    private lateinit var et_link: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnSave: Button
    private lateinit var vm: NoteViewModel
    private lateinit var myMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)
        number_picker_prior = findViewById(R.id.number_picker_priority) as NumberPicker
        et_title = findViewById(R.id.et_title) as EditText
        et_desc = findViewById(R.id.et_desc) as EditText
        et_link = findViewById(R.id.et_link) as EditText
        btnUpdate = findViewById(R.id.btnUpdate) as Button
        btnDelete = findViewById(R.id.btndelete) as Button
        btnSave = findViewById(R.id.btnsave) as Button
        number_picker_prior.minValue = 1
        number_picker_prior.maxValue = 10
        vm = ViewModelProviders.of(this)[NoteViewModel::class.java]
        btnDelete.setOnClickListener {
            deleteNote()
        }
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        vm = ViewModelProviders.of(this)[NoteViewModel::class.java]
        noteId = intent.getIntExtra(EXTRA_ID, -1)
        mode = if (noteId == -1) Mode.AddNote
        else Mode.EditNote

        when (mode) {
            Mode.AddNote -> {
                title = "Add Note"
                btnDelete.visibility = View.GONE
                btnUpdate.visibility = View.GONE
            }
            Mode.EditNote -> {
                title = "Edit Note"
                btnDelete.visibility = View.VISIBLE
                btnUpdate.visibility = View.VISIBLE
                btnSave.visibility = View.GONE
                et_title.setText(intent.getStringExtra(EXTRA_TITLE))
                et_desc.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
                et_link.setText(intent.getStringExtra(EXTRA_LINK))
                number_picker_prior.value = intent.getIntExtra(EXTRA_PRIORITY, -1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
            R.id.delete_note -> {
                deleteNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = et_title.text.toString()
        val desc = et_desc.text.toString()
        val link = et_link.text.toString()
        val priority = number_picker_prior.value


        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()
            return
        }
        vm.insert(Note(title, desc,priority,link))
        val data = Intent()
        // only if note ID was provided i.e. we are editing
        if (noteId != -1)
            data.putExtra(EXTRA_ID, noteId)
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, desc)
        data.putExtra(EXTRA_LINK, link)
        data.putExtra(EXTRA_PRIORITY, priority)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun deleteNote() {
        val title = et_title.text.toString()
        val desc = et_desc.text.toString()
        val link = et_link.text.toString()
        val priority = number_picker_prior.value


        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent()
        if (noteId != -1)
            data.putExtra(EXTRA_ID, noteId)
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, desc)
        data.putExtra(EXTRA_LINK, link)
        data.putExtra(EXTRA_PRIORITY, priority)

        setResult(Activity.RESULT_OK, data)

        vm.delete(Note(title,desc,priority,link))

        finish()
    }

    private sealed class Mode {
        object AddNote : Mode()
        object EditNote : Mode()
    }
}