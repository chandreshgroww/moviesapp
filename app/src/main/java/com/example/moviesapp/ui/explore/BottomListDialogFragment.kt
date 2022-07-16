package com.example.moviesapp.ui.explore

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MainApplication
import com.example.moviesapp.adapter.SortFilterAdapter
import com.example.moviesapp.adapter.SortFilterClickListener
import com.example.moviesapp.databinding.SortFilterDialogBinding
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.ui.MainViewModelFactory
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import javax.inject.Inject

const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    BottomListListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class BottomListDialogFragment(val viewModel: ExploreViewModel) :
    BottomSheetDialogFragment() {

    private var _binding: SortFilterDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    @Inject
//    lateinit var mainViewModelFactory: MainViewModelFactory
//
//    private lateinit var viewModel: ExploreViewModel by viewModels {  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SortFilterDialogBinding.inflate(inflater, container, false)

//        (activity?.application as MainApplication).applicationComponent.injectBottomSheetFragment(this)
//
//        viewModel = ViewModelProvider(this, mainViewModelFactory)[ExploreViewModel::class.java]

        when(tag) {
            ExploreFragment.SORT -> addSortAdapter()
            ExploreFragment.FILTER -> addFilterAdapter()
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    private fun addFilterAdapter() {
        val adapter = SortFilterAdapter(SortFilterClickListener {
            viewModel.filterMovies(it)
            dismiss()
        })
        binding.sortFilterRecyclerView.adapter = adapter
        viewModel.genreList.observe(viewLifecycleOwner, Observer { genreResult ->
            when(genreResult.status) {
                Result.Status.SUCCESS -> {
                    genreResult.data?.let {
                        viewModel.initializeGenreList(it)
                        adapter.submitList(it)
                    }
                }
                Result.Status.LOADING -> {}
                Result.Status.ERROR -> {}
            }
        })
    }

    private fun addSortAdapter() {
        val adapter = SortFilterAdapter(SortFilterClickListener {
            viewModel.sortMovies(it)
            dismiss()
        })
        binding.sortFilterRecyclerView.adapter = adapter
        viewModel.sortFilterQuery.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.sortBy)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}