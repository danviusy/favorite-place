package com.example.favorittsted.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favorittsted.data.FavorittSted
import com.example.favorittsted.repositories.FavorittStedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(private val repository: FavorittStedRepository = FavorittStedRepository()) : ViewModel() {
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

}