package com.task.weather

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import android.support.constraint.ConstraintLayout
import com.bumptech.glide.Glide
import android.support.v7.widget.Toolbar
import android.support.v7.widget.SearchView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.task.weather.fragments.FirstFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mToolbar = findViewById(R.id.my_toolbar) as Toolbar
        setSupportActionBar(mToolbar)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                passQuerytoFragments(query)
                doMySearch(query)
            }
        }

        val fragmentAdapter = PagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

//    fun passQuerytoFragments(query: String) {
//
//        val bundle = Bundle()
//        bundle.putString("query", "vilnius")
//
//        val firstFragment = FirstFragment()
//        firstFragment.setArguments(bundle)
//
//        supportFragmentManager.beginTransaction().replace(R.id.container, firstFragment).commit()
//    }



    fun doMySearch(query: String) {
        val showSearchResult = findViewById<TextView>(R.id.search_result)

        val queue = Volley.newRequestQueue(this)

        var url: String = "https://api.openweathermap.org/data/2.5/weather?q=$query&units=metric&appid=dd1cbb470e98079b87159f9a28b09359"

        var stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val jsonObjInner: JSONObject = jsonObj.getJSONObject("main")
                    val jsonObjectWeather: JSONObject = jsonObj.getJSONArray("weather").getJSONObject(0)
                    var str_weather: String = jsonObj.get("name").toString()
                    var str_temp: String = jsonObjInner.get("temp").toString()
                    var str_icon: String = jsonObjectWeather.get("icon").toString()
                    showSearchResult!!.text = "City: $str_weather\nTemperature: $str_temp °C"

                    val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
                    val imageView = ImageView(this)
                    Glide.with(this).load("http://openweathermap.org/img/w/$str_icon.png").into(imageView)
                    constraintLayout.addView(imageView)
                },
                Response.ErrorListener { showSearchResult!!.text = "That didn't work!" })
        queue.add(stringReq)
    }

//    fun toastMe(view: View) {
//        val myToast = Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT)
//        myToast.show()
//    }
//
//    fun countMe(view: View) {
//        val showCountTextView = findViewById<TextView>(R.id.textView)
//        val countString = showCountTextView.text.toString()
//        var count: Int = Integer.parseInt(countString)
//        count++
//        showCountTextView.text = count.toString()
//    }
//
//    fun randomMe(view: View) {
//        val randomIntent = Intent(this, SecondActivity::class.java)
//        val countString = textView.text.toString()
//        val count = Integer.parseInt(countString)
//        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)
//        startActivity(randomIntent)
//    }
}
