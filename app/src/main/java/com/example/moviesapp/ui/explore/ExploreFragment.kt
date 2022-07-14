package com.example.moviesapp.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.MainApplication
import com.example.moviesapp.R
import com.example.moviesapp.adapter.MovieClickListener
import com.example.moviesapp.adapter.MovieHorizontalAdapter
import com.example.moviesapp.databinding.FragmentExploreBinding
import com.example.moviesapp.ui.home.HomeViewModel
import com.example.moviesapp.ui.home.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
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
        val adapter = MovieHorizontalAdapter(MovieClickListener {
            this.findNavController()
                .navigate(ExploreFragmentDirections.actionExploreFragmentToDetailsFragment(it))
        })

        binding.moviesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        lifecycleScope.launch {
            viewModel.exploreMoviesList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            })
        }
    }

}