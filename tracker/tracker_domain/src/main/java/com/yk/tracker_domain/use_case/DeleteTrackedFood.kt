package com.yk.tracker_domain.use_case

import com.yk.tracker_domain.model.TrackedFood
import com.yk.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(trackedFood: TrackedFood) {
        repository.deleteTrackedFood(trackedFood)
    }
}