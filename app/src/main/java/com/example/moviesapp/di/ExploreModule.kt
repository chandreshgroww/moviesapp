package com.example.moviesapp.di

import androidx.lifecycle.ViewModel
import com.example.moviesapp.ui.explore.ExploreViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ExploreModule {

    @Binds
    @ClassKey(ExploreViewModel::class)
    @IntoMap
    abstract fun exploreViewModel(exploreViewModel: ExploreViewModel): ViewModel
}