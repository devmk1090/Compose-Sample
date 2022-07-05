package com.devkproject.mvvmrecipe.di

import com.devkproject.mvvmrecipe.network.RecipeService
import com.devkproject.mvvmrecipe.network.model.RecipeDtoMapper
import com.devkproject.mvvmrecipe.repository.RecipeRepository
import com.devkproject.mvvmrecipe.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeMapper: RecipeDtoMapper,
    ): RecipeRepository {
        return RecipeRepository_Impl(
            recipeService = recipeService,
            mapper = recipeMapper
        )
    }
}