package com.example.moviesapp.di

import androidx.lifecycle.ViewModel
import com.example.moviesapp.ui.details.DetailsViewModel
import com.example.moviesapp.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(HomeViewModel::class)
    @IntoMap
    abstract fun mainViewModel(homeViewModel: HomeViewModel): ViewModel

//    @Binds
//    @ClassKey(DetailsViewModel::class)
//    @IntoMap
//    abstract fun detailsViewModel(detailsViewModel: DetailsViewModel): ViewModel
}