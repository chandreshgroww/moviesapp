package com.example.moviesapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MainApplication
import com.example.moviesapp.adapter.MovieHorizontalAdapter
import com.example.moviesapp.adapter.MovieHorizontalListener
import com.example.moviesapp.adapter.MovieVerticalAdapter
import com.example.moviesapp.adapter.MovieVerticalListener
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.util.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
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
        val verticalAdapter = MovieVerticalAdapter(MovieVerticalListener {
            this.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        })
        binding.nowShowingRecyclerView.adapter = verticalAdapter

        val horizontalAdapter = MovieHorizontalAdapter()
        binding.popularRecyclerView.adapter = horizontalAdapter

        subscribeUI(binding, verticalAdapter)
    }


    private fun subscribeUI(
        binding: FragmentHomeBinding,
        verticalAdapter: MovieVerticalAdapter
    ) {
        viewModel.popularMovieList.observe(viewLifecycleOwner, Observer { result ->
            Log.i(TAG, result.status.toString())
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

}