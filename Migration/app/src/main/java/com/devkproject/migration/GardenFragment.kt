package com.devkproject.migration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.devkproject.migration.databinding.FragmentGardenBinding

class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding

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