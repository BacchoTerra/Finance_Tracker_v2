package com.bacchoterra.financetrackerv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bacchoterra.financetrackerv2.converter.Converters
import com.bacchoterra.financetrackerv2.dao.StockDao
import com.bacchoterra.financetrackerv2.model.Stock

@Database(entities = [Stock::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FinanceDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {

        @Volatile
        private var INSTANCE: FinanceDatabase? = null

        fun getInstance(context: Context): FinanceDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinanceDatabase::class.java,
                    "finance_database"
                ).fallbackToDestructiveMigration() //Allows to change DB version without migration...dangerous
                    .build()

                INSTANCE = instance
                instance
            }

        }


    }

}