package com.example.testektofinal.Data.Dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testektofinal.Data.Model.Cocktail

@Database(entities = [Cocktail::class], version = 1)
abstract class CocktailDatabase : RoomDatabase(){

    abstract fun cocktailDao() : CocktailDao

}