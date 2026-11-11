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
import com.example.androidsprints.data.model.RecipeDto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException

class MainActivity : ComponentActivity() {
    private companion object {
        const val CONNECT_TIMEOUT_MS = 5_000
        const val READ_TIMEOUT_MS = 5_000
        const val MAX_PARALLEL_REQUESTS = 10
    }

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(MAX_PARALLEL_REQUESTS)

    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null && intent?.data != null) {
            deepLinkIntent = intent
        }
        threadPool.execute {
            var connection: HttpURLConnection? = null
            var categories: List<CategoryDto>? = null
            try {
                connection =
                    URL("https://recipes.aisprints.ru/api/category").openConnection() as HttpURLConnection
                connection.connectTimeout = CONNECT_TIMEOUT_MS
                connection.readTimeout = READ_TIMEOUT_MS
                Log.i("Pool", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                val json = connection.getInputStream().bufferedReader().use { it.readText() }
                Log.i("Pool", "Запрос: ${connection.responseCode}")
                Log.i("Pool", "Запрос: ${connection.responseMessage}")
                Log.i("Pool", "Принятый json: $json")
                categories = Json.decodeFromString<List<CategoryDto>>(json)
                Log.i("Pool", "Полученный список после десериализации: $categories")
            } catch (e: IOException) {
                Log.e("Pool", "Ошибка запроса категории", e)
            } catch (e: SerializationException) {
                Log.e("Pool", "Ошибка десереализации", e)
            } finally {
                connection?.disconnect()
            }
            try {
                categories?.forEach { categories ->
                    threadPool.execute {
                        var connection: HttpURLConnection? = null
                        try {
                            connection =
                                URL("https://recipes.aisprints.ru/api/category/${categories.id}/recipes").openConnection() as HttpURLConnection
                            connection.connectTimeout = CONNECT_TIMEOUT_MS
                            connection.readTimeout = READ_TIMEOUT_MS
                            val json =
                                connection.getInputStream().bufferedReader().use { it.readText() }
                            val recipe = Json.decodeFromString<List<RecipeDto>>(json)
                            Log.i("Pool", "Имя потока: ${Thread.currentThread().name}")
                            Log.i("Pool", "Название категории: ${categories.title}")
                            Log.i("Pool", "Количество рецептов: ${recipe.size}")
                        } catch (e: IOException) {
                            Log.e("Pool", "Ошибка запроса рецептов", e)
                        } catch (e: SerializationException) {
                            Log.e("Pool", "Ошибка десереализации", e)
                        } finally {
                            connection?.disconnect()
                        }
                    }
                }
            } catch (e: RejectedExecutionException) {
                Log.e("Pool", "Пул уже закрыт, раздача остановлена", e)
            }
        }

        enableEdgeToEdge()
        setContent {
            RecipesApp(
                deepLinkIntent = deepLinkIntent,
                onDeepLink = { deepLinkIntent = null }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let {
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}
