package com.example.androidsprints.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import kotlinx.coroutines.flow.map

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)

    val uiState = favoriteManager.getFavoriteIdsFlow().map { ids ->

    }



}