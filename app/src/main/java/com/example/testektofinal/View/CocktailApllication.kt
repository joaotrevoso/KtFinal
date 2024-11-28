package com.example.testektofinal.View

import android.app.Application
import androidx.room.Room
import com.example.testektofinal.Data.Dao.CocktailDatabase

class CocktailApllication : Application() {

    lateinit var database : CocktailDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, CocktailDatabase::class.java
            , "drink-db")
            .build()
    }

    companion object {
        lateinit var instance: CocktailApllication
            private set
    }

}