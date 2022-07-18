package com.example.moviesapp.di

import com.example.moviesapp.ui.explore.ExploreFragment
import com.example.moviesapp.util.SortBy
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ExploreModule::class])
interface ExploreSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance sortBy: SortBy): ExploreSubComponent
    }

    fun inject(exploreFragment: ExploreFragment)

}