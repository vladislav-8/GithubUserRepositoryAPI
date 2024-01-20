package com.practicum.githubuserrepositoryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.githubuserrepositoryapi.data.AuthApi
import com.practicum.githubuserrepositoryapi.databinding.ActivityMainBinding
import com.practicum.githubuserrepositoryapi.domain.post_models.AuthRequest
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //username: kminchelle, kmeus4
    //password: 0lelplR, aUTdmmmbH

    private lateinit var binding: ActivityMainBinding
    private val mainUrl = "https://dummyjson.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit_auth = Retrofit.Builder()
            .baseUrl(mainUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authApi = retrofit_auth.create(AuthApi::class.java)

        binding.apply {
            button.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = authApi.auth(
                        authRequest = AuthRequest(
                            username = username.text.toString(),
                            password = password.text.toString()
                        )
                    )
                    runOnUiThread {
                        Picasso.get().load(user.image).into(iv)
                        firstName.text = user.firstName
                        lastName.text = user.lastName
                    }
                }
            }
        }
    }
}