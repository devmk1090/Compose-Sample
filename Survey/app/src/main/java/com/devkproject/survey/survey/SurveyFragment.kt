package com.devkproject.survey.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devkproject.survey.R
import com.devkproject.survey.theme.JetsurveyTheme

class SurveyFragment : Fragment() {

    private val viewModel: SurveyViewModel by viewModels {
        SurveyViewModelFactory(PhotoUriManager(requireContext().applicationContext))
    }

    private val takePicture = registerForActivityResult(TakePicture()) { photoSaved ->
        if (photoSaved) {
            viewModel.onImageSaved()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            // In order for savedState to work, the same ID needs to be used for all instances.
            id = R.id.sign_in_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                JetsurveyTheme {
                    viewModel.uiState.observeAsState().value?.let { surveyState ->
                        when (surveyState) {
                            is SurveyState.Questions -> SurveyQuestionScreen(
                                questions = surveyState,
                                shouldAskPermissions = viewModel.askForPermissions,
                                onAction = { id, action -> handleSurveyAction(id, action) },
                                onDoNotAskForPermissions = { viewModel.doNotAskForPermissions() },
                                onDonePressed = { viewModel.computeResult(surveyState) },
                                onBackPressed = {
                                    activity?.onBackPressedDispatcher?.onBackPressed()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleSurveyAction(questionId: Int, actionType: SurveyActionType) {
        when (actionType) {

        }
    }
}