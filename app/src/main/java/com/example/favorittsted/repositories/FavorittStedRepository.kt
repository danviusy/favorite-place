package com.example.favorittsted.repositories

import com.example.favorittsted.data.FavorittSted
import com.example.favorittsted.nettverk.Api

class FavorittStedRepository() {

    suspend fun getFavoritePlace(): List<FavorittSted> {
        return try {
            Api.retrofitService.getFavoritePlace()
        } catch (e: Exception) {
            emptyList()
        }
    }
}