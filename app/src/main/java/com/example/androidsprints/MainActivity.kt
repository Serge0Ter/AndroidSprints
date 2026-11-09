package com.example.androidsprints

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.androidsprints.data.model.CategoryDto
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val thread = Thread({
            try {
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                val url = URL("https://recipes.aisprints.ru/api/category")
                val connection = url.openConnection() as HttpURLConnection
                val json = connection.getInputStream().bufferedReader().readText()
                Log.i("!!!", "Запрос: ${connection.responseCode}")
                Log.i("!!!", "Запрос: ${connection.responseMessage}")
                Log.i("!!!", "Принятый json: $json")
                connection.disconnect()
                val categories = Json.decodeFromString<List<CategoryDto>>(json)
                Log.i("!!!", "Полученный список после десериализации: $categories")
            } catch (e: Exception) {
                throw e
            }
        }, "NetworkThread")
        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")
        thread.start()

        if (savedInstanceState == null && intent?.data != null) {
            deepLinkIntent = intent
        }
        enableEdgeToEdge()
        setContent {
            RecipesApp(
                deepLinkIntent = deepLinkIntent,
                onDeepLink = { deepLinkIntent = null }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let {
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}
