package com.kabelash.sliide

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import com.kabelash.sliide.data.News
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

/*
* Created by Kabelash on 14.10.2019
* */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewer()
        fetchJson()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                recyclerViewer()
                fetchJson()
                println("refresh")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Fetching JSON using OKhttp3
    private fun fetchJson() {
        val url = "https://contentapi.celltick.com/mediaApi/v1.0/content?key=t4QCg6zCkFrCW5CTJii52IAQojqNmyeJ&publisherId=Magazine_from_AppDevWebsite&userId=eccc7785-001c-4341-88f8-eddf15f3aa4a&countryCode=US&language=en&limit=100&offset=0"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                //println(body) // To get Json on Logcat

                val gson = GsonBuilder().create()

                //got Json object as array
                val news = gson.fromJson(body, News::class.java)

                runOnUiThread {
                    recyclerView.adapter = MainAdapter(news)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Request Failed $e")
            }
        })
    }

    //Adding to recyclerview
    private fun recyclerViewer(){
        val gridLayoutManager = GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                //multiple of 1 and 3
                when (position % 6) {
                    0, 1, 2, 3 -> return 5
                    4, 5 -> return 1
                }
                throw IllegalStateException("Error: Internal error")
            }
        }
        recyclerView.layoutManager = gridLayoutManager
    }
}
