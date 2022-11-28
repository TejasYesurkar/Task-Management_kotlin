package com.project.its_show_time

import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.roomdbkotlin.db.Note
import com.project.roomdbkotlin.db.NoteViewModel

class SideNavigationActivity : AppCompatActivity() {
    private lateinit var vm: NoteViewModel
    private lateinit var adapter: NoteAdapter
    private lateinit var button_add_note: FloatingActionButton
    private lateinit var recycler_view: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_navigation)
        // on below line we are creating variable for all
        // floating action buttons and a boolean variable.
        lateinit var addFAB: FloatingActionButton
        lateinit var homeFAB: FloatingActionButton
        lateinit var settingsFAB: FloatingActionButton
        var fabVisible = false

//        button_add_note = findViewById(R.id.button_add_note) as FloatingActionButton
        recycler_view = findViewById(R.id.recycler_view) as RecyclerView
        setUpRecyclerView()

        setUpListeners()

        vm = ViewModelProviders.of(this)[NoteViewModel::class.java]

        vm.getAllNotes().observe(this, Observer {
            Log.i("Notes observed", "$it")

            adapter.submitList(it)
        })





        // initializing variables of floating
        // action button on below line.
        addFAB = findViewById(R.id.idFABAdd)
        homeFAB = findViewById(R.id.idFABHome)
        settingsFAB = findViewById(R.id.idFABSettings)

        // on below line we are initializing our
        // fab visibility boolean variable
        fabVisible = false

        // on below line we are adding on click listener
        // for our add floating action button
        addFAB.setOnClickListener {
            // on below line we are checking
            // fab visible variable.
            if (!fabVisible) {

                // if its false we are displaying home fab
                // and settings fab by changing their
                // visibility to visible.
                homeFAB.show()
                settingsFAB.show()

                // on below line we are setting
                // their visibility to visible.
                homeFAB.visibility = View.VISIBLE
                settingsFAB.visibility = View.VISIBLE

                // on below line we are checking image icon of add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_close_24))

                // on below line we are changing
                // fab visible to true
                fabVisible = true
            } else {

                // if the condition is true then we
                // are hiding home and settings fab
                homeFAB.hide()
                settingsFAB.hide()

                // on below line we are changing the
                // visibility of home and settings fab
                homeFAB.visibility = View.GONE
                settingsFAB.visibility = View.GONE

                // on below line we are changing image source for add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))

                // on below line we are changing
                // fab visible to false.
                fabVisible = false
            }
        }

        // on below line we are adding
        // click listener for our home fab
        homeFAB.setOnClickListener {
            Toast.makeText(this@SideNavigationActivity, "Add New Task", Toast.LENGTH_LONG).show()
            // on below line we are displaying a toast message.
            startActivity(Intent(applicationContext, AddEditTask::class.java))
        }

        // on below line we are adding on
        // click listener for settings fab
        settingsFAB.setOnClickListener {
            startActivity(Intent(applicationContext, DailyExpenseActivity::class.java))
            // on below line we are displaying a toast message
            Toast.makeText(this@SideNavigationActivity, "Daily Expense..", Toast.LENGTH_LONG).show()
        }
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

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = NoteAdapter { text, clickedNote ->
            if (text.equals("delete")) {
                val intent = Intent(this, AddEditTask::class.java)
                intent.putExtra(EXTRA_ID, clickedNote.id)
                intent.putExtra(EXTRA_TITLE, clickedNote.title)
                intent.putExtra(EXTRA_DESCRIPTION, clickedNote.description)
                intent.putExtra(EXTRA_PRIORITY, clickedNote.priority)
                startActivityForResult(intent, DELETE_NOTE_REQUEST)

            } else if (text.equals("edit")) {

                val intent = Intent(this, AddEditTask::class.java)
                intent.putExtra(EXTRA_ID, clickedNote.id)
                intent.putExtra(EXTRA_TITLE, clickedNote.title)
                intent.putExtra(EXTRA_DESCRIPTION, clickedNote.description)
                intent.putExtra(EXTRA_LINK, clickedNote.docLink)
                intent.putExtra(EXTRA_PRIORITY, clickedNote.priority)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }

        }
        recycler_view.adapter = adapter
    }
    fun clickAdapater(url:String) {
        Log.d("TAGG","Side Nav")
        var intent:Intent
        intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)

        startActivity(intent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String = data.getStringExtra(EXTRA_TITLE).toString()
            val description: String = data.getStringExtra(EXTRA_DESCRIPTION).toString()
            val link: String =data.getStringExtra(EXTRA_LINK).toString()
            val priority: Int = data.getIntExtra(EXTRA_PRIORITY, -1)
//            vm.insert(Note(title, description,priority,link))
            Toast.makeText(this, "Event added Successfully!", Toast.LENGTH_SHORT).show()

        } else if (data != null && requestCode == DELETE_NOTE_REQUEST) {
            val id = data.getIntExtra(EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Event couldn't be updated!", Toast.LENGTH_SHORT).show()
                return
            }
            val title: String = data.getStringExtra(EXTRA_TITLE).toString()
            val description: String =data.getStringExtra(EXTRA_DESCRIPTION).toString()
            val link: String =data.getStringExtra(EXTRA_LINK).toString()
            val priority: Int = data.getIntExtra(EXTRA_PRIORITY, -1)
            vm.delete(Note(title, description, priority,link, id))
            Toast.makeText(this, "Event updated!", Toast.LENGTH_SHORT).show()

        }else if (data != null && requestCode == EDIT_NOTE_REQUEST) {
            val id = data.getIntExtra(EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Event couldn't be updated!", Toast.LENGTH_SHORT).show()
                return
            }
            val title: String = data.getStringExtra(EXTRA_TITLE).toString()
            val description: String =data.getStringExtra(EXTRA_DESCRIPTION).toString()
            val link: String =data.getStringExtra(EXTRA_LINK).toString()
            val priority: Int = data.getIntExtra(EXTRA_PRIORITY, -1)
            vm.update(Note(title, description, priority,link, id))
            finish();
            startActivity(getIntent());
            Toast.makeText(this, "Event updated!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Event not saved!", Toast.LENGTH_SHORT).show()
        }
    }

}