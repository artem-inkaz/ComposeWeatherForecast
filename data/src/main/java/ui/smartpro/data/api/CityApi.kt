package ui.smartpro.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ui.smartpro.data.dto.city_search.CitiesResponse
import javax.inject.Singleton

@Singleton
interface CityApi {

    @GET(value = "/v1/geo/cities")
    suspend fun getCityList(
        @Query("namePrefix") namePrefix: String,
        @Query("languageCode") languageCode: String
    ): CitiesResponse
}