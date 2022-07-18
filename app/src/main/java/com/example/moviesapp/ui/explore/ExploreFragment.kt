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
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.MainApplication
import com.example.moviesapp.adapter.MovieClickListener
import com.example.moviesapp.adapter.MoviePagingAdapter
import com.example.moviesapp.databinding.FragmentExploreBinding
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.paging.LoaderAdapter
import com.example.moviesapp.ui.MainViewModelFactory
import com.example.moviesapp.util.SortBy
import kotlinx.coroutines.flow.collectLatest
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

        val exploreComponent = (activity?.application as MainApplication).applicationComponent.exploreComponent().create(args.sortBy)
        exploreComponent.inject(this)

        viewModel = ViewModelProvider(this, mainViewModelFactory)[ExploreViewModel::class.java]

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        initializeAdapter()

        initializeClickListeners()

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

        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.moviesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }

//        adapter.loadStateFlow.collectLatest { loadState ->
//            when (val currentState = loadState.refresh) {
//                is LoadState.Loading -> {
//
//                }
//                is LoadState.Error -> {
//                    val extractedException = currentState.error // SomeCatchableException
//
//                }
//            }
//        }

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