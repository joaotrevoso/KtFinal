package com.example.testektofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testektofinal.Data.Model.Cocktail
import com.example.testektofinal.Data.Model.CocktailResponse
import com.example.testektofinal.Data.Model.RetrofitInstance
import com.example.testektofinal.View.UserInputScreen
import com.example.testektofinal.ui.theme.TesteKtoFinalTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TesteKtoFinalTheme {

                val cock = remember { mutableStateOf<List<Cocktail>>(emptyList()) }


                //MainScreen()

                UserInputScreen(cock)

            }
        }
    }
}

@Composable
fun MainScreen() {

    var cock by remember { mutableStateOf<List<Cocktail>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.getRandomCocktail().enqueue(object : Callback<CocktailResponse> {
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { cocktailResponse ->
                        cock = cocktailResponse.drinks
                    }
                } else {
                    errorMessage = "Erro ao carregar os dados: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                errorMessage = "Falha na rede: ${t.message}"
            }
        })
    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (cock.isNotEmpty()) {
            Greeting(name = cock[0].strDrink, modifier = Modifier.padding(innerPadding))

        } else {
            Text(
                text = errorMessage ?: "Carregando...",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "gohoghsdj $name!",
        modifier = modifier
    )
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TesteKtoFinalTheme {
        Greeting("Android")
    }
}
