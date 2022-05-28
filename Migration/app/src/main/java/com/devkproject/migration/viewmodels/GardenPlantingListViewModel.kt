package com.devkproject.migration.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.devkproject.migration.data.GardenPlantingRepository
import com.devkproject.migration.data.PlantAndGardenPlantings

class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {
    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()
}