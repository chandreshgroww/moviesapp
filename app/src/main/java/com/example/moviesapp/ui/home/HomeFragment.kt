package com.example.moviesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.MainActivity
import com.example.moviesapp.MainApplication
import com.example.moviesapp.R
import com.example.moviesapp.adapter.MovieHorizontalAdapter
import com.example.moviesapp.adapter.MovieHorizontalListener
import com.example.moviesapp.adapter.MovieVerticalAdapter
import com.example.moviesapp.adapter.MovieVerticalListener
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.repository.MovieRepository
import javax.inject.Inject

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var homeViewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        (activity?.application as MainApplication).applicationComponent.injectHome(this)

        viewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initializeAdapters()

        return binding.root
    }

    private fun initializeAdapters() {
        initializeNowShowing()
        initializePopular()
    }

    private fun initializePopular() {
        val adapter = MovieHorizontalAdapter(MovieHorizontalListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        })
        binding.popularRecyclerView.adapter = adapter
    }

    private fun initializeNowShowing() {
        val adapter = MovieVerticalAdapter(MovieVerticalListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        })
        binding.nowShowingRecyclerView.adapter = adapter
    }

}