package com.raccooncode.ktorclientandroidtheme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raccooncode.ktorclientandroidtheme.data.remote.PostsService
import com.raccooncode.ktorclientandroidtheme.data.remote.dto.PostResponse
import com.raccooncode.ktorclientandroidtheme.ui.theme.KtorClientAndroidThemeTheme

class MainActivity : ComponentActivity() {
    private val service = PostsService.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorClientAndroidThemeTheme {
                val posts = produceState(
                    initialValue = emptyList<PostResponse>(),
                    producer =  {
                        value = service.getPosts()
                    }
                )
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.d("hello my dog","hi")
                    LazyColumn {
                        items(posts.value) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                            ) {
                                Text(
                                    text = it.title
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = it.body
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
