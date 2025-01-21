package com.app.networky.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.networky.ui.components.ProfileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _profileData = MutableStateFlow<ProfileData?>(null)
    val profileData = _profileData.asStateFlow()

    fun updateProfile(newProfile: ProfileData) {
        viewModelScope.launch {
            // тут позже нужно добавить сохранение в бд или на сервер
            _profileData.value = newProfile
        }
    }
}