package com.oc.kitscanner.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.oc.domain.services.ScannerService
import com.oc.scanner.MlKitScanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideAnalyticsService(
        activity: Activity
    ): ScannerService {
        return MlKitScanner(activity as AppCompatActivity)
    }

}