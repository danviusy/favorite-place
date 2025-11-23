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


    // UI-state variablene
    private val _uiState = MutableStateFlow(PlaceUiState())
    val uiState: StateFlow<PlaceUiState> = _uiState.asStateFlow()


    // Liste av alle favoritt stedene
    private val _favoritePlaces = MutableStateFlow<List<FavorittSted>>(emptyList())
    val favoritePlaces: StateFlow<List<FavorittSted>> = _favoritePlaces


    init {
        getFavoritePlace()
    }


    // Henter ut alle stedene
    fun getFavoritePlace() {
        viewModelScope.launch {
            val list = repository.getFavoritePlace()
            _favoritePlaces.value = list
        }
    }

    // Legger inn et nytt lagret sted

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


    // Resetter UI-state variablene
    fun resetStateValues() {
        _uiState.value = PlaceUiState()
    }


    // Oppdaterer UI-state variablene
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


    // Oppdaterer UI-state variablene når en ny sted er lagt inn
    fun onCreationOfPlace(Latitude: Double, Longitude: Double) {
        _uiState.value = PlaceUiState(
            currentLatitude = Latitude,
            currentLongitude = Longitude
        )
    }

    // Oppdaterer detaljene om et sted
    fun updatePlace(id: Int, name: String, description: String, address: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                repository.updatePlace(id, name, description, address, latitude, longitude)
                getFavoritePlace()
            } catch (e : Exception) {
                Log.e("PlaceViewModel", "Feil ved oppdatering av data: ${e.message}")
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