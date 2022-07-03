package com.devkproject.mvvmrecipe.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.devkproject.mvvmrecipe.domain.Recipe
import com.devkproject.mvvmrecipe.util.DEFAULT_RECIPE_IMAGE
import com.devkproject.mvvmrecipe.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RecipeView(
    recipe: Recipe,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            val image = loadPicture(url = recipe.featuredImage, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Recipe Featured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGHT.dp)
                    ,
                    contentScale = ContentScale.Crop,
                )
            }
            Column(

            ) {

            }
        }
    }
}