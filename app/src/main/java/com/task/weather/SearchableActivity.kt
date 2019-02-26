package com.task.weather

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class SearchableActivity : AppCompatActivity() {

    private var searchResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }
    }

    fun doMySearch(query: String) {

        val showSearchResult = findViewById<TextView>(R.id.search_result)

        showSearchResult.text = "TEXT"
    }
}