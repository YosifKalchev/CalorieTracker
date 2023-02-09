package com.yk.calorietracker.navigation

import androidx.navigation.NavController
import com.yk.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}