package com.devkproject.accessibility.data.posts

import com.devkproject.accessibility.model.Post
import com.devkproject.accessibility.utils.addOrRemove
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Simplified implementation of PostsRepository that returns a hardcoded list of
 * posts with resources synchronously.
 */
class PostsRepository {
    // for now, keep the favorites in memory
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    /**
     * Get a specific JetNews post.
     */
    fun getPost(postId: String?): Post? {
        return posts.find { it.id == postId }
    }

    /**
     * Get JetNews posts.
     */
    fun getPosts(): List<Post> {
        return posts
    }

    /**
     * Observe the current favorites
     */
    fun observeFavorites(): Flow<Set<String>> = favorites

    /**
     * Toggle a postId to be a favorite or not.
     */
    fun toggleFavorite(postId: String) {
        val set = favorites.value.toMutableSet()
        set.addOrRemove(postId)
        favorites.value = set
    }
}