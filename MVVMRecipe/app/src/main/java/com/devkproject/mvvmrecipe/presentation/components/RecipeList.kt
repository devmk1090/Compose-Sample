package com.devkproject.mvvmrecipe.presentation.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.devkproject.mvvmrecipe.domain.Recipe
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToRecipeDetailScreen: (Int) -> Unit,
) {
    
}