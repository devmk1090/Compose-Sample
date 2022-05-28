package com.devkproject.migration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.devkproject.migration.databinding.FragmentGardenBinding
import com.devkproject.migration.utilities.InjectorUtils
import com.devkproject.migration.viewmodels.GardenPlantingListViewModel

class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding

    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater, container, false)

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }
        return binding.root
    }

    private fun navigateToPlantListPage() {
//        requireActivity().findViewById<ViewPager2>(R.id.view_pager)
    }
}