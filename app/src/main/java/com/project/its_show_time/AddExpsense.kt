package com.project.its_show_time

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.project.roomdbkotlin.db.Expense
import com.project.roomdbkotlin.db.ExpenseViewModel
import kotlinx.android.synthetic.main.activity_add_edit_task.*
import kotlinx.android.synthetic.main.activity_add_expsense.*
import kotlinx.android.synthetic.main.activity_add_expsense.view.*
import java.util.*


class AddExpsense : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var textDate: EditText
    private lateinit var editResoan: EditText
    private lateinit var editAmount : EditText
    private lateinit var exp_date: String
    private lateinit var exp_type: String
    private lateinit var vm: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expsense)
        spinner = findViewById(R.id.spinnerType) as Spinner
        editResoan = findViewById(R.id.et_reason) as EditText
        editAmount = findViewById(R.id.et_amount) as EditText

        vm = ViewModelProviders.of(this)[ExpenseViewModel::class.java]
        var types = resources.getStringArray(R.array.investment)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, types
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@AddExpsense,
                        types[position], Toast.LENGTH_SHORT
                    ).show()
                    exp_type =  types[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val textDate = findViewById(R.id.et_date) as TextView
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast

                exp_date = "$dayOfMonth/${monthOfYear + 1}/$year"
                val msg = "Date Of Expense : "+exp_date
                textDate.text = msg
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    fun saveData(view: View) {
        var date =exp_date
        var typeOfExpense =exp_type
        var resoanOfExpense =editResoan.text
        var amt =Integer.parseInt(et_amount.text.toString())


        vm.insert(Expense(typeOfExpense,date,amt, resoanOfExpense.toString()))
        finish()
    }

}