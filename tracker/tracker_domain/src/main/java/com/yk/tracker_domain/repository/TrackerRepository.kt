package com.yk.tracker_domain.repository

import com.yk.tracker_domain.model.TrackableFood

interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pagesSize: Int
    ): Result<TrackableFood>
}