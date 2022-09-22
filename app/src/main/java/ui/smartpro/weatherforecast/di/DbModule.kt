package ui.smartpro.weatherforecast.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ui.smartpro.common.Constants.CITIES_DATABASE_NAME
import ui.smartpro.data.db.CitiesDatabase
import ui.smartpro.data.db.dao.CitiesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideCitiesDatabase(@ApplicationContext context: Context): CitiesDatabase = Room
        .databaseBuilder(context, CitiesDatabase::class.java, CITIES_DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideCitiesDao(db: CitiesDatabase): CitiesDao = db.citiesDao()

}