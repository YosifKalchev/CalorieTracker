package com.yk.tracker_domain.use_case

import com.yk.tracker_domain.model.TrackableFood
import com.yk.tracker_domain.model.TrackedFood
import com.yk.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import java.time.LocalDate

class GetFoodsForDate(
    private val repository: TrackerRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(date)
    }
}