package com.example.moviesapp.ui.explore

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentItemListDialogListDialogBinding

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
//class BottomListListDialogFragment : BottomSheetDialogFragment() {
//
//    private var _binding: FragmentItemListDialogListDialogBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}