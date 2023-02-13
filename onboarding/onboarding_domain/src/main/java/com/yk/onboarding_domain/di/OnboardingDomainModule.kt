package com.yk.onboarding_domain.di

import com.yk.onboarding_domain.use_case.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class OnboardingDomainModule {


    @Provides
    @ViewModelScoped
    fun providesValidateNutrientsUseCase(): ValidateNutrients {
        return ValidateNutrients()
    }
}