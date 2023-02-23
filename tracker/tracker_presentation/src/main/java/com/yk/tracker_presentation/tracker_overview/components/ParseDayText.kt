package com.yk.tracker_presentation.tracker_overview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yk.core.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun parseDayText(date: LocalDate): String {
    val today = LocalDate.now()
    return when(date) {
        today -> stringResource(id = R.string.today)
        today.minusDays(1) -> stringResource(id = R.string.previous_day)
        today.plusDays(1) -> stringResource(id = R.string.next_day)
        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }
}