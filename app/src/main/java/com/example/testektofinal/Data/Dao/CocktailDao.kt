package com.example.testektofinal.Data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.testektofinal.Data.Model.Cocktail

@Dao
interface CocktailDao {

    @Insert
    suspend fun inserir(drink: Cocktail)

    @Update
    suspend fun atualizar(drink: Cocktail)

    @Query("SELECT * FROM drinks")
    suspend fun getAll() : List<Cocktail>

    @Query("DELETE FROM drinks WHERE idDrink = :id")
    suspend fun deletear(id: Int)

}