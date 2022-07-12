package com.example.moviesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.R
import com.example.moviesapp.adapter.MovieHorizontalAdapter
import com.example.moviesapp.adapter.MovieHorizontalListener
import com.example.moviesapp.adapter.MovieVerticalAdapter
import com.example.moviesapp.adapter.MovieVerticalListener
import com.example.moviesapp.databinding.FragmentHomeBinding

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initializeNowShowing()

        initializePopular()

        return binding.root
    }

    private fun initializePopular() {
        val adapter = MovieHorizontalAdapter(MovieHorizontalListener {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
        })
        binding.popularRecyclerView.adapter = adapter
    }

    private fun initializeNowShowing() {
        val adapter = MovieVerticalAdapter(MovieVerticalListener {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
        })
        binding.nowShowingRecyclerView.adapter = adapter
    }

}