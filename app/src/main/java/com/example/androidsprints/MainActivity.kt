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
import androidx.lifecycle.lifecycleScope
import com.example.androidsprints.core.network.Retrofit
import com.example.androidsprints.core.network.api.RecipeApiService
import com.example.androidsprints.data.model.CategoryDto
import com.example.androidsprints.data.model.RecipeDto
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null && intent?.data != null) {
            deepLinkIntent = intent
        }

        lifecycleScope.launch {
            Log.i("Pool", "Имя потока: ${Thread.currentThread().name}")
            val apiService = Retrofit.retrofit.create(RecipeApiService::class.java)
            val categories: List<CategoryDto> = try {
                apiService.getCategories()
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("Pool", "Сервер ответил ошибкой: ${e.code()}", e)
                return@launch
            } catch (e: IOException) {
                Log.e("Pool", "Ошибка запроса", e)
                return@launch
            } catch (e: Exception) {
                Log.e("Pool", "Не удалось загрузить категории", e)
                return@launch
            }
            coroutineScope {
                categories.forEach { category ->
                    launch {
                        Log.i("Pool", "Имя потока: ${Thread.currentThread().name}")
                        try {
                            val recipes: List<RecipeDto> =
                                apiService.getRecipesByCategory(category.id)
                            Log.i("Pool", "Название категории: ${category.title}")
                            Log.i("Pool", "Количество рецептов: ${recipes.size}")
                        } catch (e: CancellationException) {
                            throw e
                        } catch (e: HttpException) {
                            Log.e("Pool", "Сервер ответил ошибкой", e)
                        } catch (e: IOException) {
                            Log.e("Pool", "Ошибка запроса", e)
                        } catch (e: Exception) {
                            Log.e("Pool", "Ошибка десереализации", e)
                        }
                    }
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let {
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}
