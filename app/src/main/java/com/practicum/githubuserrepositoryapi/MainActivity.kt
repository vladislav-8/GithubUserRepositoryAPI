package com.practicum.githubuserrepositoryapi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.practicum.githubuserrepositoryapi.data.GithubApi
import com.practicum.searchcompose.models.GithubModelItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity  : ComponentActivity() {

    val baseUrl = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val githubApi = retrofit.create(GithubApi::class.java)
    val repositories = ArrayList<GithubModelItem>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val searchText = remember {
                mutableStateOf("")
            }

            val isActive = remember {
                mutableStateOf(false)
            }

            val data = remember { mutableStateListOf<GithubModelItem>() }

            Column {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    query = searchText.value,
                    onQueryChange = { text ->
                        searchText.value = text
                    },
                    onSearch = { text ->
                        isActive.value = false
                        CoroutineScope(Dispatchers.IO).launch {
                            val list = githubApi.getRepositories(text)
                            runOnUiThread {
                                data.clear()
                                data.addAll(list)
                                Log.d("TAG", "$repositories")
                            }
                        }
                    },
                    active = isActive.value,
                    onActiveChange = {
                        isActive.value = it
                    },
                    placeholder = {
                        Text(
                            text = "Search..."
                        )
                    },
                ) {
                    Text(
                        "search history"
                    )
                }
                LazyColumn {
                    items(data.toList()) { data ->
                        OneRow(data = data)
                    }
                }
            }

        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun OneRow(data: GithubModelItem) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(CornerSize(10.dp))

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(data.owner.avatar_url),
                    contentDescription = "image",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(150.dp)
                        .clip(RoundedCornerShape(CornerSize(10.dp)))
                )

                Text(text = "${data.owner.login} \n ${data.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Toast
                                .makeText(
                                    applicationContext,
                                    "You clicked on " + data.owner.login,
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                )
            }
        }
    }
}