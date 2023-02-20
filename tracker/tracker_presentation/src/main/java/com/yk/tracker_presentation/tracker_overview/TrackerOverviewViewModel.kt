package com.yk.tracker_presentation.tracker_overview

import androidx.lifecycle.ViewModel
import com.yk.core.domain.preferences.Preferences
import com.yk.core.util.UiEvent
import com.yk.tracker_domain.use_case.TrackerUsesCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUsesCases
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when(event) {
            is TrackerOverviewEvent.OnAddFoodClick -> {

            }
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {

            }
            is TrackerOverviewEvent.OnNextDayClick -> {

            }
            is TrackerOverviewEvent.OnPreviousDayClick -> {

            }
            is TrackerOverviewEvent.OnToggleMealClick -> {

            }
        }
    }
}