package com.example.moviesapp.ui.explore

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MainApplication
import com.example.moviesapp.adapter.MovieClickListener
import com.example.moviesapp.adapter.MoviePagingAdapter
import com.example.moviesapp.databinding.FragmentExploreBinding
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.paging.LoaderAdapter
import com.example.moviesapp.ui.MainViewModelFactory
import com.example.moviesapp.util.SortBy
import javax.inject.Inject

private const val TAG = "ExploreFragment"

class ExploreFragment : Fragment() {

    lateinit var viewModel: ExploreViewModel
    lateinit var binding: FragmentExploreBinding

    private val args: ExploreFragmentArgs by navArgs()

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExploreBinding.inflate(inflater)

        (activity?.application as MainApplication).applicationComponent.injectExplore(this)

        viewModel = ViewModelProvider(this, mainViewModelFactory)[ExploreViewModel::class.java]

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        initializeAdapter()

        initializeClickListeners()

        viewModel.sortMovies(SortFilter(-1, args.sortBy.displayName, args.sortBy.notation, false))

        return binding.root
    }

    private fun initializeClickListeners() {
        binding.exploreBackArrow.setOnClickListener {
            this.findNavController().popBackStack()
        }

        binding.bottomSortButton.setOnClickListener {
            BottomListDialogFragment(viewModel).show(childFragmentManager, SORT)
        }

        binding.bottomFilterButton.setOnClickListener {
            BottomListDialogFragment(viewModel).show(childFragmentManager, FILTER)
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

        viewModel.moviesList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        })
    }

    companion object {
        const val SORT = "sort"
        const val FILTER = "filter"
    }

}