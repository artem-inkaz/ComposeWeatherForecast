package ui.smartpro.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ui.smartpro.data.db.dao.CitiesDao
import ui.smartpro.data.db.entities.CityEntity

@Database(entities = [CityEntity::class], version = 2, exportSchema = false)
abstract class CitiesDatabase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao

}