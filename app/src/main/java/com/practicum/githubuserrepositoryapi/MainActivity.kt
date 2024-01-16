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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

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
            hhApi.getVacancies(editText.text.toString()).enqueue(object :
                Callback<List<GithubApiModelItem>> {
                override fun onResponse(call: Call<List<GithubApiModelItem>>,
                                        response: Response<List<GithubApiModelItem>>
                ) {
                    if (response.code() == 200) {
                        vacancies.clear()
                        if (response.body()?.isNotEmpty() == true) {
                            vacancies.addAll(response.body()!!)
                            myAdapter.notifyDataSetChanged()
                            Toast.makeText(this@MainActivity, "НАШЛОСЬ", Toast.LENGTH_SHORT).show()
                        }
                        if (vacancies.isEmpty()) {
                            Toast.makeText(this@MainActivity, "НИЧЕГО", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<GithubApiModelItem>>, t: Throwable) {
                    //Log.d("TAG", t.toString())
                }
            })
        }
    }
}