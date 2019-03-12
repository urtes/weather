package com.task.weather

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.bumptech.glide.Glide
import android.support.v7.widget.Toolbar
import android.support.v7.widget.SearchView
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var topToolbar = findViewById<Toolbar>(R.id.top_toolbar)
        setSupportActionBar(topToolbar)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                bundle.putString("location", query)

            }
        }

        val fragmentAdapter = PagerAdapter(supportFragmentManager, bundle)

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

    fun doMySearch(query: String) {


//        val temp = findViewById<TextView>(R.id.temp)
//        val city = findViewById<TextView>(R.id.city)
//        val weatherConditions = findViewById<TextView>(R.id.weather_conditions)
//        val detailConditions = findViewById<TextView>(R.id.detail_conditions)
//
//
//        val queue = Volley.newRequestQueue(this)
//
//        var url: String = "https://api.openweathermap.org/data/2.5/weather?q=$query&units=metric&appid=dd1cbb470e98079b87159f9a28b09359"
//
//        var stringReq = StringRequest(Request.Method.GET, url,
//                Response.Listener<String> { response ->
//
//                    var strResp = response.toString()
//                    val jsonObj: JSONObject = JSONObject(strResp)
//                    val jsonObjInner: JSONObject = jsonObj.getJSONObject("main")
//                    val jsonObjectWeather: JSONObject = jsonObj.getJSONArray("weather").getJSONObject(0)
//
//                    var str_city: String = jsonObj.get("name").toString()
//                    var str_temp: String = jsonObjInner.get("temp").toString()
//                    var str_icon: String = jsonObjectWeather.get("icon").toString()
//                    var str_conditions: String = jsonObjectWeather.get("main").toString().toUpperCase()
//                    val str_description: String = jsonObjectWeather.get("description").toString()
//                    val str_pressure: String = jsonObjInner.get("pressure").toString()
//                    val str_humidity: String = jsonObjInner.get("humidity").toString()
//                    val str_visibility: String = jsonObj.get("visibility").toString()
//
//                    city?.text = str_city
//                    temp?.text = "$str_temp C°"
//                    weatherConditions?.text = str_conditions
//                    detailConditions?.text = "$str_description\nPressure: $str_pressure\nHumidity: $str_humidity\nVisibility: $str_visibility"
//
//                    var imgUrl = "http://openweathermap.org/img/w/$str_icon.png"
//                    val imageView = findViewById<ImageView>(R.id.weather_icon)
//                    Glide.with(this).load(imgUrl).into(imageView)
//
//                },
//                Response.ErrorListener { temp!!.text = "That didn't work!" })
//        queue.add(stringReq)
    }
}
