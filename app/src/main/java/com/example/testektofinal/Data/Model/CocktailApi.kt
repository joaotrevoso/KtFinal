package com.example.testektofinal.Data.Model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    @GET("search.php")
    fun getCocktailByName(@Query("s") cocktailName : String) : Call<CocktailResponse>

    @GET("search.php")
    fun getCocktailByFirstLetter(@Query("f") firstLetter : String) : Call<CocktailResponse>

}

object RetrofitInstance {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val api : CocktailApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailApi::class.java)
    }
}