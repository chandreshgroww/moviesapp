package com.example.moviesapp.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MainApplication
import com.example.moviesapp.adapter.MovieClickListener
import com.example.moviesapp.adapter.MoviePagingAdapter
import com.example.moviesapp.databinding.FragmentExploreBinding
import com.example.moviesapp.paging.LoaderAdapter
import com.example.moviesapp.ui.MainViewModelFactory
import javax.inject.Inject

class ExploreFragment : Fragment() {

    lateinit var viewModel: ExploreViewModel
    lateinit var binding: FragmentExploreBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExploreBinding.inflate(inflater)

        (activity?.application as MainApplication).applicationComponent.injectExplore(this)

        viewModel = ViewModelProvider(this, mainViewModelFactory)[ExploreViewModel::class.java]

        initializeAdapter()

        initializeClickListeners()

        return binding.root
    }

    private fun initializeClickListeners() {
        binding.exploreBackArrow.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

    private fun initializeAdapter() {
        val adapter = MoviePagingAdapter(MovieClickListener {
            this.findNavController()
                .navigate(ExploreFragmentDirections.actionExploreFragmentToDetailsFragment(it))
        })

        binding.moviesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }

        viewModel.exploreMoviesList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        })
    }

}