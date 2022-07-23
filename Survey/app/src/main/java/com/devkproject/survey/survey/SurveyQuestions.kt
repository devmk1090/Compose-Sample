package com.devkproject.survey.survey

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devkproject.survey.R
import com.devkproject.survey.theme.JetsurveyTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.util.*

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
    Column(modifier) {
        Spacer(modifier = Modifier.height(32.dp))

    }
}

@Composable
private fun QuestionContent(
    question: Question,
    answer: Answer<*>?,
    onAnswer: (Answer<*>) -> Unit,
    onAction: (Int, SurveyActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
            QuestionTitle(question.questionText)
            Spacer(modifier = Modifier.height(24.dp))
            if (question.description != null) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(id = question.description),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(bottom = 18.dp, start = 8.dp, end = 8.dp)
                    )
                }
            }
            when (question.answer) {

            }
        }
    }
}

@Composable
private fun QuestionTitle(@StringRes title: Int) {
    val backgroundColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.onSurface.copy(alpha = 0.04f)
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.06f)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.small
            )
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        )
    }
}

@Composable
private fun SingleChoiceQuestion(
    possibleAnswer: PossibleAnswer.SingleChoice,
    answer: Answer.SingleChoice?,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswer.optionsStringRes.associateBy { stringResource(id = it) }

    val radioOptions = options.keys.toList()

    val selected = if (answer != null) {
        stringResource(id = answer.answer)
    } else {
        null
    }

    val (selectedOption, onOptionSelected) = remember(answer) { mutableStateOf(selected) }

    Column(modifier = modifier) {
        radioOptions.forEach { text ->
            val onClickHandle = {
                onOptionSelected(text)
                options[text]?.let { onAnswerSelected(it) }
                Unit
            }
            val optionSelected = text == selectedOption

            val answerBorderColor = if (optionSelected) {
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            }
            val answerBackgroundColor = if (optionSelected) {
                MaterialTheme.colors.primary.copy(alpha = 0.12f)
            } else {
                MaterialTheme.colors.background
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = answerBorderColor
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = optionSelected,
                            onClick = onClickHandle
                        )
                        .background(answerBackgroundColor)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = text
                    )

                    RadioButton(
                        selected = optionSelected,
                        onClick = onClickHandle,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        }
    }
}

/**
 * Returns the start of today in milliseconds
 */
fun getDefaultDateInMillis(): Long {
    val cal = Calendar.getInstance()
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val date = cal.get(Calendar.DATE)
    cal.clear()
    cal.set(year, month, date)
    return cal.timeInMillis
}

@Preview
@Composable
fun QuestionPreview() {
    val question = Question(
        id = 2,
        questionText = R.string.pick_superhero,
        answer = PossibleAnswer.SingleChoice(
            optionsStringRes = listOf(
                R.string.spark,
                R.string.lenz,
                R.string.bugchaos,
                R.string.frag
            )
        ),
        description = R.string.select_one
    )
    JetsurveyTheme {
        Question(
            question = question,
            shouldAskPermissions = true,
            answer = null,
            onAnswer = {},
            onAction = { _, _ -> },
            onDoNotAskForPermissions = {}
        )
    }
}