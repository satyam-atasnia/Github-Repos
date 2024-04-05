package com.satyam.githubrepos

import android.app.Application
import com.satyam.githubrepos.data.database.AppDatabase
import com.satyam.githubrepos.data.network.GithubApi
import com.satyam.githubrepos.data.preference.PreferenceProvider
import com.satyam.githubrepos.data.repository.DetailsRepository
import com.satyam.githubrepos.data.repository.SearchRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubApplication : Application() {

    companion object {
        lateinit var instance: Application private set


        fun provideGithubApi() = GithubApi()

        fun provideAppDatabase() = AppDatabase(instance)

        fun providePreferences() = PreferenceProvider(instance)

        fun provideSearchRepo(): SearchRepository = SearchRepository(
            provideGithubApi(),
            provideAppDatabase(),
            providePreferences(),
            instance
        )

        fun provideDetailsRepo(): DetailsRepository = DetailsRepository(
            provideGithubApi(),
            provideAppDatabase(),
            instance
        )
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
