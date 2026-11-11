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
import com.example.androidsprints.data.network.ApiClient
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException

class MainActivity : ComponentActivity() {
    private val client = ApiClient.client

    private companion object {
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
            Log.i("Pool", "Имя потока: ${Thread.currentThread().name}")
            var categories: List<CategoryDto>? = null
            try {
                val categoryRequest =
                    Request.Builder().url("https://recipes.aisprints.ru/api/category").build()
                val categoryAsString = client.newCall(categoryRequest).execute().use { response ->
                    Log.i("Pool", "Запрос: ${response.code}")
                    Log.i("Pool", "Запрос: ${response.message}")
                    response.body.string()
                }
                categories = Json.decodeFromString<List<CategoryDto>>(categoryAsString)
                Log.i("Pool", "Полученный список после десериализации: $categories")
            } catch (e: IOException) {
                Log.e("Pool", "Ошибка запроса", e)
            } catch (e: SerializationException) {
                Log.e("Pool", "Ошибка десереализации", e)
            }
            categories?.forEach { categories ->
                try {
                    threadPool.execute {
                        Log.i("Pool", "Имя потока: ${Thread.currentThread().name}")
                        var recipes: List<RecipeDto>?
                        try {
                            val recipesRequest = Request.Builder()
                                .url("https://recipes.aisprints.ru/api/category/${categories.id}/recipes")
                                .build()
                            val recipesAsString =
                                client.newCall(recipesRequest).execute().use { recipes ->
                                    Log.i("Pool", "Запрос: ${recipes.code}")
                                    Log.i("Pool", "Запрос: ${recipes.message}")
                                    recipes.body.string()
                                }
                            recipes = Json.decodeFromString<List<RecipeDto>>(recipesAsString)
                            Log.i("Pool", "Название категории: ${categories.title}")
                            Log.i("Pool", "Количество рецептов: ${recipes.size}")
                        } catch (e: IOException) {
                            Log.e("Pool", "Ошибка запроса", e)
                        } catch (e: SerializationException) {
                            Log.e("Pool", "Ошибка десереализации", e)
                        }
                    }
                } catch (e: RejectedExecutionException) {
                    Log.e("Pool", "Пул уже закрыт, раздача остановлена", e)
                }
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
