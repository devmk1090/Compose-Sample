package com.devkproject.survey.survey

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import kotlinx.coroutines.launch

const val simpleDataFormatPattern = "EEE, MMM d"

class SurveyViewModel(
    private val surveyRepository: SurveyRepository,
    private val photoUriManager: PhotoUriManager
) : ViewModel() {

    private val _uiState = MutableLiveData<SurveyState>()
    val uiState: LiveData<SurveyState>
        get() = _uiState

    var askForPermissions by mutableStateOf(true)
        private set

    private lateinit var surveyInitialState: SurveyState

    // Uri used to save photos taken with the camera
    private var uri: Uri? = null

    init {
        viewModelScope.launch {
            val survey = surveyRepository.getSurvey()

            //Create the default questions state based on the survey questions
            val questions: List<QuestionState> = survey.questions.mapIndexed { index, question ->
                val showPrevious = index > 0
                val showDone = index == survey.questions.size - 1
                QuestionState(
                    question = question,
                    questionIndex = index,
                    totalQuestionsCount = survey.questions.size,
                    showPrevious = showPrevious,
                    showDone = showDone
                )
            }
            surveyInitialState = SurveyState.Questions(survey.title, questions)
            _uiState.value = surveyInitialState
        }
    }

    fun computeResult(surveyQuestions: SurveyState.Questions) {
        val answers = surveyQuestions.questionsState.mapNotNull { it.answer }
        val result = surveyRepository.getSurveyResult(answers)
        _uiState.value = SurveyState.Result(surveyQuestions.surveyTitle, result)
    }

    fun onDatePicked(questionId: Int, pickerSelection: Long) {
        updateStateWithActionResult(
            questionId,
            SurveyActionResult.Date(pickerSelection)
        )
    }

    fun getCurrentDate(questionId: Int): Long {
        return getSelectedDate(questionId)
    }

    fun getUriToSaveImage(): Uri? {
        uri = photoUriManager.buildNewUri()
        return uri
    }

    fun onImageSaved() {
        uri?.let { uri ->

        }
    }

    // TODO: Ideally this should be stored in the database
    fun doNotAskForPermissions() {
        askForPermissions = false
    }

    private fun updateStateWithActionResult(questionId: Int, result: SurveyActionResult) {
        val latestState = _uiState.value
        if (latestState != null && latestState is SurveyState.Questions) {
            val question =
                latestState.questionsState.first { questionState ->
                    questionState.question.id == questionId
                }
            question.answer = Answer.Action(result)
            question.enableNext = true
        }
    }

    private fun getLatestQuestionId(): Int? {
        val latestState = _uiState.value
        if (latestState != null && latestState is SurveyState.Questions) {
            return latestState.questionsState[latestState.currentQuestionIndex].question.id
        }
        return null
    }

    private fun getSelectedDate(questionId: Int): Long {
        val latestState = _uiState.value
        if (latestState != null && latestState is SurveyState.Questions) {
            val question =
                latestState.questionsState.first { questionState ->
                    questionState.question.id == questionId
                }
            val answer = question.answer as Answer.Action?
            if (answer != null && answer.result is SurveyActionResult.Date) {
                return answer.result.dateMillis
            }
        }
        return getDefaultDateInMillis()
    }
}

class SurveyViewModelFactory(
    private val photoUriManager: PhotoUriManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(JetpackSurveyRepository, photoUriManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
