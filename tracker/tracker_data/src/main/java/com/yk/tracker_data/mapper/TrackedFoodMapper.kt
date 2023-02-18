package com.yk.tracker_data.mapper

import com.yk.tracker_data.local.entity.TrackedFoodEntity
import com.yk.tracker_domain.model.MealType
import com.yk.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amountInGrams = amountInGrams,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amountInGrams = amountInGrams,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = calories,
        id = id
    )
}