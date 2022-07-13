package com.example.moviesapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.MainApplication
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.util.NetworkResult

private const val TAG = "DetailsFragment"

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        val repository = (activity?.application as MainApplication).movieRepository
        viewModel = ViewModelProvider(
            this,
            DetailsViewModelFactory(args.movie, repository)
        )[DetailsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        addClickListener()

        return binding.root
    }

    private fun addClickListener() {
        binding.detailsBackArrow.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }
}