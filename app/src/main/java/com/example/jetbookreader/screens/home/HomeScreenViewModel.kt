package com.example.jetbookreader.screens.home

import androidx.lifecycle.ViewModel
import com.example.jetbookreader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository
): ViewModel()  {
}