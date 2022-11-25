package com.project.its_show_time

import CustomAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity(),OnClikInterface {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.ic_launcher_background, "Item " + i,"https://1drv.ms/w/s!AuQ3HZhoo9VRgz41mEvFSDfw0YYP?e=jMLD5g"))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }

    override suspend fun onClickItem() {
        Log.d(">>","Home")
        startActivity(Intent(applicationContext, WebViewActivity::class.java))
    }

    override fun onclickK() {
        startActivity(Intent(applicationContext, WebViewActivity::class.java))
    }
    fun clickAdapater(text: String) {
        Log.d(">>", "HomeActivity")
        val intent:Intent  =Intent(applicationContext, WebViewActivity::class.java)
        intent.putExtra("url",text)
        startActivity(intent)

    }
}