package ui.smartpro.data.api.dto.city_search

import com.google.gson.annotations.SerializedName
import kotlin.Metadata

data class CitiesResponse(
    @SerializedName("data") val `data`: List<Data>,
    @SerializedName("metadata") val metadata: Metadata
)
