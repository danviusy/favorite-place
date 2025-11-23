package com.example.favorittsted.repositories


import com.example.favorittsted.data.FavorittSted
import com.example.favorittsted.nettverk.Api

class FavorittStedRepository() {

    suspend fun getFavoritePlace(): List<FavorittSted> { // Henter alle stedsopplysningene
        return try {
            Api.retrofitService.getFavoritePlace()
        } catch (e: Exception) {
            emptyList()
        }
    }


    // Legger inn et nytt favoritt sted
    suspend fun putPlace(name: String, description: String, address: String, latitude: Double, longitude: Double) {
        try {
            Api.retrofitService.putPlace(name, description, address, latitude.toString(), longitude.toString())
        } catch (e: Exception) {
            println("Feil ved lagring av data: ${e.message}")
        }
    }

    // Oppdaterer detaljer om et favoritt sted
    suspend fun updatePlace(id: Int, name: String, description: String, address: String, latitude: Double, longitude: Double) {
        try {
            Api.retrofitService.updatePlace(id, name, description, address, latitude.toString(), longitude.toString())
        } catch (e: Exception) {
            println("Feil ved oppdatering av data: ${e.message}")
        }
    }


    // Sletter et lagret sted
    suspend fun deletePlace(id: Int) {
        try {
            Api.retrofitService.deletePlace(id)
        } catch (e: Exception) {
            println("Feil ved sletting av data: ${e.message}")
        }
    }

}