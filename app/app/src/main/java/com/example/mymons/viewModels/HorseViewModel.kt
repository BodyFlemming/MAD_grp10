package com.example.mymons.viewModels

import androidx.lifecycle.ViewModel
import com.example.mymons.models.Horse
import com.example.mymons.services.HorseService
import com.example.mymons.services.HorseServiceInterface
import kotlinx.coroutines.flow.MutableStateFlow

class HorseViewModel(private val service: HorseServiceInterface = HorseService()) : ViewModel() {
    private val _horses = MutableStateFlow<List<Horse>>(emptyList())
//    val uiState = _horses.asStateFlow()
}