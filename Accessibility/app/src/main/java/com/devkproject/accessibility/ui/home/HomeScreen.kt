package com.devkproject.accessibility.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devkproject.accessibility.R
import com.devkproject.accessibility.data.posts.PostsRepository
import com.devkproject.accessibility.model.Post
import com.devkproject.accessibility.ui.components.InsetAwareTopAppBar
import com.devkproject.accessibility.ui.theme.JetnewsTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlinx.coroutines.launch

/**
 * Stateful HomeScreen which manages state using [produceUiState]
 *
 * @param postsRepository data source for this screen
 * @param navigateToArticle (event) request navigation to Article screen
 * @param openDrawer (event) request opening the app drawer
 * @param scaffoldState (state) state for the [Scaffold] component on this screen
 */
@Composable
fun HomeScreen(
    postsRepository: PostsRepository,
    navigateToArticle: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    HomeScreen(
        posts = postsRepository.getPosts(),
        navigateToArticle = navigateToArticle,
        openDrawer = openDrawer,
        scaffoldState = scaffoldState
    )
}

/**
 * Responsible for displaying the Home Screen of this application.
 *
 * Stateless composable is not coupled to any specific state management.
 *
 * @param posts (state) the data to show on the screen
 * @param favorites (state) favorite posts
 * @param onToggleFavorite (event) toggles favorite for a post
 * @param navigateToArticle (event) request navigation to Article screen
 * @param openDrawer (event) request opening the app drawer
 * @param scaffoldState (state) state for the [Scaffold] component on this screen
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    posts: List<Post>,
    navigateToArticle: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val title = stringResource(id = R.string.app_name)
            InsetAwareTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { openDrawer() } }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_jetnews_logo),
                            contentDescription = stringResource(R.string.cd_open_navigation_drawer)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        PostList(posts, navigateToArticle, modifier)
    }
}

/**
 * Display a list of posts.
 *
 * When a post is clicked on, [navigateToArticle] will be called to navigate to the detail screen
 * for that post.
 *
 * @param posts (state) the list to display
 * @param navigateToArticle (event) request navigation to Article screen
 * @param modifier modifier for the root element
 */
@Composable
private fun PostList(
    posts: List<Post>,
    navigateToArticle: (postId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val postsHistory = posts.subList(0, 3)
    val postsPopular = posts.subList(3, 5)

    LazyColumn(
        modifier = modifier,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = false
        )
    ) {
        items(postsHistory) { post ->
            PostCardHistory(post, navigateToArticle)
            PostListDivider()
        }
        item {
            PostListPopularSection(postsPopular, navigateToArticle)
        }
    }
}

/**
 * Horizontal scrolling cards for [PostList]
 *
 * @param posts (state) to display
 * @param navigateToArticle (event) request navigation to Article screen
 */
@Composable
private fun PostListPopularSection(
    posts: List<Post>,
    navigateToArticle: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.home_popular_section_title),
            style = MaterialTheme.typography.subtitle1
        )

        LazyRow(contentPadding = PaddingValues(end = 16.dp)) {
            items(posts) { post ->
                PostCardPopular(
                    post,
                    navigateToArticle,
                    Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
            }
        }
        PostListDivider()
    }
}

/**
 * Full-width divider with padding for [PostList]
 */
@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Preview("Home screen")
@Preview("Home screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Home screen (big font)", fontScale = 1.5f)
@Preview("Home screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun PreviewHomeScreen() {
    JetnewsTheme {
        HomeScreen(
            posts = PostsRepository().getPosts(),
            navigateToArticle = { /*TODO*/ },
            openDrawer = { /*TODO*/ },
            scaffoldState = rememberScaffoldState()
        )
    }
}