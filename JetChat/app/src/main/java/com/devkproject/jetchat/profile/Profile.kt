package com.devkproject.jetchat.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devkproject.jetchat.FunctionalityNotAvailablePopup
import com.devkproject.jetchat.R
import com.devkproject.jetchat.components.AnimatingFabContent
import com.devkproject.jetchat.components.JetchatAppBar
import com.devkproject.jetchat.components.baselineHeight
import com.devkproject.jetchat.data.colleagueProfile
import com.devkproject.jetchat.data.meProfile
import com.devkproject.jetchat.theme.JetchatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userData: ProfileScreenState, onNavIconPressed: () -> Unit = { }) {

    var functionalityNotAvailablePopupShown by remember { mutableStateOf(false) }
    if (functionalityNotAvailablePopupShown) {
        FunctionalityNotAvailablePopup { functionalityNotAvailablePopupShown = false }
    }

    val scrollState = rememberScrollState()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        JetchatAppBar(
            // Use statusBarsPadding() to move the app bar content below the status bar
            modifier = Modifier.statusBarsPadding(),
            scrollBehavior = scrollBehavior,
            onNavIconPressed = onNavIconPressed,
            title = { },
            actions = {
                //More icon
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .clickable(onClick = { functionalityNotAvailablePopupShown = true })
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                        .height(24.dp),
                    contentDescription = stringResource(id = R.string.more_options)
                )
            }
        )
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ProfileHeader(
                        scrollState,
                        userData,
                        this@BoxWithConstraints.maxHeight
                    )
                    UserInfoFields(userData, this@BoxWithConstraints.maxHeight)
                }
            }
            ProfileFab(
                extended = scrollState.value == 0,
                userIsMe = userData.isMe(),
                modifier = Modifier.align(Alignment.BottomEnd),
                onFabClicked = { functionalityNotAvailablePopupShown = true }
            )
        }
    }
}

@Composable
private fun UserInfoFields(userData: ProfileScreenState, containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        NameAndPosition(userData)

        ProfileProperty(stringResource(R.string.display_name), userData.displayName)

        ProfileProperty(stringResource(R.string.status), userData.status)

        ProfileProperty(stringResource(R.string.twitter), userData.twitter, isLink = true)

        userData.timeZone?.let {
            ProfileProperty(stringResource(R.string.timezone), userData.timeZone)
        }

        // Add a spacer that always shows part (320.dp) of the fields list regardless of the device,
        // in order to always leave some content at the top.
        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
private fun NameAndPosition(
    userData: ProfileScreenState
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Name(
            userData,
            modifier = Modifier.baselineHeight(32.dp)
        )
        Position(
            userData,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .baselineHeight(24.dp)
        )
    }
}

@Composable
private fun Name(userData: ProfileScreenState, modifier: Modifier = Modifier) {
    Text(
        text = userData.name,
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun Position(userData: ProfileScreenState, modifier: Modifier = Modifier) {
    Text(
        text = userData.position,
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun ProfileHeader(
    scrollState: ScrollState,
    data: ProfileScreenState,
    containerHeight: Dp
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    data.photo?.let {
        Image(
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth()
                // TODO: Update to use offset to avoid recomposition
                .padding(
                    start = 16.dp,
                    top = offsetDp,
                    end = 16.dp
                )
                .clip(CircleShape),
            painter = painterResource(id = it),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun ProfileProperty(label: String, value: String, isLink: Boolean = false) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        Text(
            text = label,
            modifier = Modifier.baselineHeight(24.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val style = if (isLink) {
            MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
        } else {
            MaterialTheme.typography.bodyLarge
        }
        Text(
            text = value,
            modifier = Modifier.baselineHeight(24.dp),
            style = style
        )
    }
}

@Composable
fun ProfileError() {
    Text(stringResource(R.string.profile_error))
}

@Composable
fun ProfileFab(
    extended: Boolean,
    userIsMe: Boolean,
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit = { }
) {
    key(userIsMe) {
        FloatingActionButton(
            onClick = onFabClicked,
            modifier = modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .height(48.dp)
                .widthIn(min = 48.dp),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            AnimatingFabContent(
                icon = {
                    Icon(
                        imageVector = if (userIsMe) Icons.Outlined.Create else Icons.Outlined.Chat,
                        contentDescription = stringResource(
                            if (userIsMe) R.string.edit_profile else R.string.message
                        )
                    )
                },
                text = {
                    Text(
                        text = stringResource(
                            id = if (userIsMe) R.string.edit_profile else R.string.message
                        ),
                    )
                },
                extended = extended
            )
        }
    }
}

@Preview(widthDp = 640, heightDp = 360)
@Composable
fun ConvPreviewLandscapeMeDefault() {
    JetchatTheme {
        ProfileScreen(meProfile)
    }
}

@Preview(widthDp = 360, heightDp = 480)
@Composable
fun ConvPreviewPortraitMeDefault() {
    JetchatTheme {
        ProfileScreen(meProfile)
    }
}

@Preview(widthDp = 360, heightDp = 480)
@Composable
fun ConvPreviewPortraitOtherDefault() {
    JetchatTheme {
        ProfileScreen(colleagueProfile)
    }
}