package com.example.moviesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.MainApplication
import com.example.moviesapp.adapter.MovieHorizontalAdapter
import com.example.moviesapp.adapter.MovieListAdapter
import com.example.moviesapp.adapter.MovieClickListener
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.util.Result
import com.google.android.material.snackbar.Snackbar
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
        val verticalAdapter = MovieListAdapter(0, MovieClickListener {
            this.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        })
        binding.nowShowingRecyclerView.adapter = verticalAdapter

        val horizontalAdapter = MovieListAdapter(1, MovieClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        })
        binding.popularRecyclerView.adapter = horizontalAdapter

        subscribeUI(binding, verticalAdapter, horizontalAdapter)
    }


    private fun subscribeUI(
        binding: FragmentHomeBinding,
        verticalAdapter: MovieListAdapter,
        horizontalAdapter: MovieListAdapter
    ) {
        addVerticalMovieObserver(verticalAdapter, binding)
        addHorizontalMovieObserver(horizontalAdapter, binding)
    }

    private fun addVerticalMovieObserver(
        verticalAdapter: MovieListAdapter,
        binding: FragmentHomeBinding
    ) {
        viewModel.popularMovieList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { verticalAdapter.submitList(it) }
                }
                Result.Status.LOADING -> {}
                Result.Status.ERROR -> {
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun addHorizontalMovieObserver(
        horizontalAdapter: MovieListAdapter,
        binding: FragmentHomeBinding
    ) {
        viewModel.voteCountMovieList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { horizontalAdapter.submitList(it) }
                }
                Result.Status.LOADING -> {}
                Result.Status.ERROR -> {
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

}