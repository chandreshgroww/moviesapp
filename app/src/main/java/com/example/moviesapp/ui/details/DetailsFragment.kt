package com.example.moviesapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.MainApplication
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Result
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var repository: MovieRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        (activity?.application as MainApplication).applicationComponent.injectDetails(this)

        viewModel = ViewModelProvider(
            this,
            DetailsViewModelFactory(args.movie, repository)
        )[DetailsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        addClickListener()

        subscribeUI()

        return binding.root
    }

    private fun addClickListener() {
        binding.detailsBackArrow.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

    private fun subscribeUI() {
        viewModel.movieDetail.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.movie = result.data
                }
                Result.Status.LOADING -> {}
                Result.Status.ERROR -> {
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}