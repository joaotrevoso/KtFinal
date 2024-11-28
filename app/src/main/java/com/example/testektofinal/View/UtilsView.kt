package com.example.testektofinal.View

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.testektofinal.Data.Model.Cocktail
import com.example.testektofinal.Data.Model.CocktailResponse
import com.example.testektofinal.Data.Model.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun UserInputScreen(cocktails: MutableState<List<Cocktail>>) {

    var userInput by remember { mutableStateOf("") }
    var letterInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Digite o nome do drink abaixo:",
            style = MaterialTheme.typography.titleMedium
        )

        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Seu texto aqui") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {

                obterDrinkPorNome(userInput, cocktails, { errorMessage = it })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        Text(
            text = "Clique Para Obter Um Drink Aleatorio",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = {
                obterDrinkAleatorio(cocktails,{errorMessage = it})
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obter")
        }

        Text(
            text = "Insira a primeira letra do Drink:",
            style = MaterialTheme.typography.titleMedium
        )


        TextField(
            value = letterInput,
            onValueChange = { txt ->
                if (txt.length <= 1) {
                    letterInput = txt
                } else {
                    letterInput = txt.substring(0, 1)
                }
            },
            label = { Text("Letra") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                obterDrinkPrimeiraLetra(letterInput, cocktails, { errorMessage = it })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obter")
        }


        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

            CocktailListScreen(cocktails.value)

    }
}

fun obterDrinkPorNome(
    name: String,
    cocktails: MutableState<List<Cocktail>>,
    onError: (String) -> Unit
) {

    RetrofitInstance.api.getCocktailByName(name).enqueue(object : Callback<CocktailResponse> {
        override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
            if (response.isSuccessful) {
                response.body()?.let { cocktailResponse ->
                    cocktails.value = cocktailResponse.drinks ?: emptyList()
                    onError("")
                }
            } else {
                onError("Erro ao carregar os dados: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
            onError("Erro : ${t.message}")
        }
    })
}

fun obterDrinkAleatorio(
    cocktails: MutableState<List<Cocktail>>,
    onError: (String) -> Unit
) {
    RetrofitInstance.api.getRandomCocktail().enqueue(object : Callback<CocktailResponse> {
        override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
            if (response.isSuccessful) {
                response.body()?.let { cocktailResponse ->
                    cocktails.value = cocktailResponse.drinks ?: emptyList()
                    onError("")
                }
            } else {
                onError("Erro ao carregar os dados: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
            onError("Falha na rede: ${t.message}")
        }
    })
}

fun obterDrinkPrimeiraLetra(
    letter : String,
    cocktails: MutableState<List<Cocktail>>,
    onError: (String) -> Unit
) {
    RetrofitInstance.api.getCocktailByFirstLetter(letter).enqueue(object : Callback<CocktailResponse> {
        override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
            if (response.isSuccessful) {
                response.body()?.let { cocktailResponse ->
                    cocktails.value = cocktailResponse.drinks ?: emptyList()
                    onError("")
                }
            } else {
                onError("Erro ao carregar os dados: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
            onError("Falha na rede: ${t.message}")
        }
    })
}

@Composable
fun CocktailListScreen(cocktails: List<Cocktail>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val iter = cocktails.iterator()

        itemsIndexed(cocktails){index,cocktail ->
            CocktailDetailItem(cocktail)
        }

    }
}

@Composable
fun CocktailDetailItem(cocktail: Cocktail) {

    var isLiked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        AsyncImage(
            model = cocktail.strDrinkThumb,
            contentDescription = cocktail.strDrink,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = cocktail.strDrink,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Text(
            text = "Categoria: ${cocktail.strCategory}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )


        if (!cocktail.strIBA.isNullOrEmpty()) {
            Text(
                text = "IBA: ${cocktail.strIBA}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }


        Text(
            text = "Instruções:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Text(
            text = cocktail.strInstructions ?: "Sem instruções disponíveis.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Text(
            text = "Ingredientes:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        IconButton(
            onClick = {

                isLiked = !isLiked
            },
            modifier = Modifier.align(Alignment.End)
        ) {

            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Curtir",
                tint = if (isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


