package com.yk.tracker_presentation.search

data class SearchState(
    val query: String = "",
    val isHitVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFoodUiState> = emptyList()
)
