package com.example.moviesapp.di

import androidx.lifecycle.ViewModel
import com.example.moviesapp.ui.explore.ExploreViewModel
import com.example.moviesapp.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(HomeViewModel::class)
    @IntoMap
    abstract fun mainViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @ClassKey(ExploreViewModel::class)
    @IntoMap
    abstract fun exploreViewModel(exploreViewModel: ExploreViewModel): ViewModel
}