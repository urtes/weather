package com.task.weather

import android.app.DownloadManager
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import android.content.Context.SEARCH_SERVICE
import android.widget.SearchView


class MainActivity : AppCompatActivity() {

    private var textViewJson: TextView? = null
    private var currentWeather: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById(R.id.searchView) as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

//        textViewJson = findViewById<TextView>(R.id.tv_users)
//        currentWeather = findViewById<TextView>(R.id.weather)
//
//        getUsers()
//        getWeather()

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }


    }

    fun doMySearch(query: String) {

        val showSearchResult = findViewById<TextView>(R.id.search_result)

        val queue = Volley.newRequestQueue(this)

        var url: String = "https://api.openweathermap.org/data/2.5/weather?q=$query&units=metric&appid=dd1cbb470e98079b87159f9a28b09359"

        var stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val jsonObjInner: JSONObject = jsonObj.getJSONObject("main")
                    var str_weather: String = jsonObj.get("name").toString()
                    var str_temp: String = jsonObjInner.get("temp").toString()
                    showSearchResult!!.text = "City: $str_weather\nTemperature: $str_temp"
                },
                Response.ErrorListener { showSearchResult!!.text = "That didn't work!" })
        queue.add(stringReq)
    }

    fun toastMe(view: View) {
        val myToast = Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT)
        myToast.show()
    }

    fun countMe(view: View) {
        val showCountTextView = findViewById<TextView>(R.id.textView)
        val countString = showCountTextView.text.toString()
        var count: Int = Integer.parseInt(countString)
        count++
        showCountTextView.text = count.toString()
    }

    fun randomMe(view: View) {
        val randomIntent = Intent(this, SecondActivity::class.java)
        val countString = textView.text.toString()
        val count = Integer.parseInt(countString)
        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)
        startActivity(randomIntent)
    }

//    fun getUsers() {
//        val queue = Volley.newRequestQueue(this)
//        val url: String = "https://api.github.com/search/users?q=eyehunt"
//
//        val stringReq = StringRequest(Request.Method.GET, url,
//                Response.Listener<String> { response ->
//
//                    var strResp = response.toString()
//                    val jsonObj: JSONObject = JSONObject(strResp)
//                    val jsonArray: JSONArray = jsonObj.getJSONArray("items")
//                    var str_user: String = ""
//                    for (i in 0 until jsonArray.length()) {
//                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
//                        str_user = str_user + "\n" + jsonInner.get("login")
//                    }
//                    textViewJson!!.text = "response : $str_user "
//                },
//                Response.ErrorListener { textViewJson!!.text = "That didn't work!" })
//        queue.add(stringReq)
//    }

    fun getWeather() {
        val queue = Volley.newRequestQueue(this)
        var url: String = "https://api.openweathermap.org/data/2.5/weather?q=vilnius&appid=dd1cbb470e98079b87159f9a28b09359"

        var stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    var str_weather: String = jsonObj.get("name").toString()
                    currentWeather!!.text = "City: $str_weather"
                },
                Response.ErrorListener { currentWeather!!.text = "That didn't work!" })
        queue.add(stringReq)
    }
}
