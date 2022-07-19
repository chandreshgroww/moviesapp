package com.example.moviesapp.ui.explore

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapp.adapter.SortFilterAdapter
import com.example.moviesapp.adapter.SortFilterClickListener
import com.example.moviesapp.databinding.SortFilterDialogBinding
import com.example.moviesapp.util.Result

class BottomListDialogFragment(val viewModel: ExploreViewModel) :
    BottomSheetDialogFragment() {

    private var _binding: SortFilterDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SortFilterDialogBinding.inflate(inflater, container, false)

        when(tag) {
            ExploreFragment.SORT -> addSortAdapter()
            ExploreFragment.FILTER -> addFilterAdapter()
        }

        return binding.root

    }

    private fun addFilterAdapter() {
        val adapter = SortFilterAdapter(SortFilterClickListener {
            viewModel.filterMovies(it)
            dismiss()
        })
        binding.sortFilterRecyclerView.adapter = adapter
        viewModel.genreList.observe(viewLifecycleOwner) { genreResult ->
            when (genreResult.status) {
                Result.Status.SUCCESS -> {
                    genreResult.data?.let {
                        viewModel.initializeGenreList(it)
                        adapter.submitList(it)
                    }
                }
                Result.Status.LOADING -> {}
                Result.Status.ERROR -> {}
            }
        }
    }

    private fun addSortAdapter() {
        val adapter = SortFilterAdapter(SortFilterClickListener {
            viewModel.sortMovies(it)
            dismiss()
        })
        binding.sortFilterRecyclerView.adapter = adapter
        viewModel.sortFilterQuery.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it.sortBy)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}