package com.example.android.news_kotlin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import java.util.*
import kotlin.collections.ArrayList
class MainActivity : AppCompatActivity(), NewsItemClicked {


    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView : RecyclerView = findViewById(R.id.recyclerView)
        listView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        listView.adapter = mAdapter
        val swipeup : SwipeRefreshLayout = findViewById(R.id.swipeup)
        swipeup.setOnRefreshListener {

            fetchData()
            swipeup.isRefreshing = false
        }
    }

    private fun fetchData() {
        checkConnection()
        val scroolbar : ProgressBar = findViewById(R.id.progressBar3)
        scroolbar.visibility = View.VISIBLE
        val url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=nation"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("image")
                    )
                    newsArray.add(news)
                    Collections.shuffle(newsArray)
                    scroolbar.visibility = View.GONE
                }

                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Failed! To Load Data , Try Later ",Toast.LENGTH_LONG).show()
                scroolbar.visibility = View.GONE
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
     fun checkConnection(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo? = connectivityManager.activeNetworkInfo
        if(networkInfo!=null && networkInfo.isConnected()){
            val  widi = networkInfo.type==ConnectivityManager.TYPE_WIFI
            val net = networkInfo.type==ConnectivityManager.TYPE_MOBILE

            if(widi || net){
                //Toast.makeText(this,"conneted",Toast.LENGTH_LONG).show()
                val nointernet : ImageView = findViewById(R.id.noInternet)
                nointernet.visibility = View.GONE
            }
            else{
                //Toast.makeText(this,"no",Toast.LENGTH_LONG).show()
                val noInternet: ImageView = findViewById(R.id.noInternet)
                noInternet.visibility = View.VISIBLE

            }
        }
    }
}