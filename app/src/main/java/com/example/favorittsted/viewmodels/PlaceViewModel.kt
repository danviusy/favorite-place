package com.example.favorittsted.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favorittsted.data.FavorittSted
import com.example.favorittsted.data.PlaceUiState
import com.example.favorittsted.repositories.FavorittStedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(private val repository: FavorittStedRepository = FavorittStedRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow(PlaceUiState())
    val uiState: StateFlow<PlaceUiState> = _uiState.asStateFlow()

    private val _favoritePlaces = MutableStateFlow<List<FavorittSted>>(emptyList())
    val favoritePlaces: StateFlow<List<FavorittSted>> = _favoritePlaces

    init {
        getFavoritePlace()
    }

    fun getFavoritePlace() {
        viewModelScope.launch {
            val list = repository.getFavoritePlace()
            _favoritePlaces.value = list
        }
    }

    fun putFavoritePlace(name: String, description: String, address: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                repository.putPlace(name, description, address, latitude, longitude)
                _uiState.value = PlaceUiState()
                getFavoritePlace()
            } catch (e: Exception) {
                Log.e("PlaceViewModel", "Feil ved lagring av data: ${e.message}")
                _uiState.value = PlaceUiState()
            }

        }
    }

    fun resetStateValues() {
        _uiState.value = PlaceUiState()
    }

    fun updateStateValues(id : Int, name: String, description: String, address: String, latitude: Double, longitude: Double) {
        _uiState.value = PlaceUiState(
            currentId = id,
            currentName = name,
            currentDescription = description,
            currentAddress = address,
            currentLatitude = latitude,
            currentLongitude = longitude
        )
    }

    fun onCreationOfPlace(Latitude: Double, Longitude: Double) {
        _uiState.value = PlaceUiState(
            currentLatitude = Latitude,
            currentLongitude = Longitude
        )
    }

    fun updatePlace(id: Int, name: String, description: String, address: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                repository.updatePlace(id, name, description, address, latitude, longitude)
                _uiState.value = PlaceUiState()
                getFavoritePlace()
            } catch (e : Exception) {
                Log.e("PlaceViewModel", "Feil ved oppdatering av data: ${e.message}")
                _uiState.value = PlaceUiState()
            }
        }
    }

    fun deletePlace(id: Int) {
        viewModelScope.launch {
            try {
                repository.deletePlace(id)
                _uiState.value = PlaceUiState()
                getFavoritePlace()
            } catch (e : Exception) {
                Log.e("PlaceViewModel", "Feil ved sletting av data: ${e.message}")
                _uiState.value = PlaceUiState()
            }
        }
    }


}