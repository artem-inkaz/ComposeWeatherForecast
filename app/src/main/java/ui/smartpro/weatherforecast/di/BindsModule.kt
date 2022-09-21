package ui.smartpro.weatherforecast.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ui.smartpro.domain.interactors.WeatherIconsInteractor
import ui.smartpro.domain.interactors.WeatherStringsInteractor
import ui.smartpro.domain.repositories.cities.CitiesRepository
import ui.smartpro.domain.repositories.cities.CitiesRepositoryImpl
import ui.smartpro.domain.repositories.datastore.DataStoreRepository
import ui.smartpro.domain.repositories.datastore.DataStoreRepositoryImpl
import ui.smartpro.domain.repositories.forecast.ForecastRepository
import ui.smartpro.domain.repositories.forecast.ForecastRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface BindsModule {

    @Binds
    fun bindForecastRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

    @Binds
    fun bindCityRepository(cityRepositoryImpl: CitiesRepositoryImpl): CitiesRepository

    @Binds
    fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository
}