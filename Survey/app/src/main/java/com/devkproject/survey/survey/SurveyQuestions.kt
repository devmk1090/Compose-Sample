package com.devkproject.survey.survey

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Question(
    question: Question,
    answer: Answer<*>?,
    shouldAskPermissions: Boolean,
    onAnswer: (Answer<*>) -> Unit,
    onAction: (Int, SurveyActionType) -> Unit,
    onDoNotAskForPermissions: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (question.permissionsRequired.isEmpty()) {
        QuestionContent(question, answer, onAnswer, onAction, modifier)
    } else {
        val multiplePermissionsState = rememberMultiplePermissionsState(
            question.permissionsRequired
        )

        if (multiplePermissionsState.allPermissionsGranted) {
            QuestionContent(question, answer, onAnswer, onAction, modifier)
        } else {
            PermissionsRationale(
                question,
                multiplePermissionsState,
                onDoNotAskForPermissions,
                modifier.padding(horizontal = 20.dp)
            )
        }

        // If we cannot ask for permissions, inform the caller that can move to the next question
        if (!shouldAskPermissions) {
            LaunchedEffect(true) {
                onAnswer(Answer.PermissionsDenied)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionsRationale(
    question: Question,
    multiplePermissionsState: MultiplePermissionsState,
    onDoNotAskForPermissions: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun QuestionContent(
    question: Question,
    answer: Answer<*>?,
    onAnswer: (Answer<*>) -> Unit,
    onAction: (Int, SurveyActionType) -> Unit,
    modifier: Modifier = Modifier
) {

}