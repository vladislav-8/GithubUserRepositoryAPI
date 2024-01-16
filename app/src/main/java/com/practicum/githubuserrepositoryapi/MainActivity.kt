package com.practicum.githubuserrepositoryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.githubuserrepositoryapi.data.GithubApi
import com.practicum.githubuserrepositoryapi.databinding.ActivityMainBinding
import com.practicum.githubuserrepositoryapi.domain.GithubApiModelItem
import com.practicum.githubuserrepositoryapi.presentation.ReposAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val baseUrl = "https://api.github.com/"

    lateinit var editText: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var searchButton: Button

    lateinit var myAdapter: ReposAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val hhApi = retrofit.create(GithubApi::class.java)

    private val vacancies = ArrayList<GithubApiModelItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editTextText)
        recyclerView = findViewById(R.id.recyclerView)
        searchButton = findViewById(R.id.buttonSearch)

        searchButton.setOnClickListener {
            search()
        }

        initRecycler()
    }

    fun initRecycler() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            myAdapter = ReposAdapter()
            adapter = myAdapter
            myAdapter.vacancy = vacancies
        }
    }

    fun search() {
        if (editText.text.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val list = hhApi.getVacancies(editText.text.toString())
                runOnUiThread {
                    vacancies.clear()
                    vacancies.addAll(list)
                    myAdapter.notifyDataSetChanged()
                    Log.d("TAG", "$vacancies")
                }
            }
        }
    }
}