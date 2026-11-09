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
            val connection: HttpURLConnection =
                URL("https://recipes.aisprints.ru/api/category").openConnection() as HttpURLConnection
            try {
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                val json = connection.getInputStream().bufferedReader().use { it.readText() }
                Log.i("!!!", "Запрос: ${connection.responseCode}")
                Log.i("!!!", "Запрос: ${connection.responseMessage}")
                Log.i("!!!", "Принятый json: $json")
                val categories = Json.decodeFromString<List<CategoryDto>>(json)
                Log.i("!!!", "Полученный список после десериализации: $categories")
            } catch (e: Exception) {
                Log.e("!!!", "Ошибка запроса", e)
            } finally {
                connection.disconnect()
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
