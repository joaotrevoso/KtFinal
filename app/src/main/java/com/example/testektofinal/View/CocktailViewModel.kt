package com.example.testektofinal.View

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testektofinal.Data.Model.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CocktailViewModel(appli: CocktailApllication) : ViewModel() {

    private val cocktailDao = appli.database.cocktailDao()
    private val _cocktailList = MutableStateFlow<List<Cocktail>>(emptyList())

    init {
        listarTodos()
    }

    @Suppress("unused")
    constructor() : this(CocktailApllication.instance)

    fun listarTodos() {
        viewModelScope.launch {
            _cocktailList.value = cocktailDao.getAll()
        }
    }

    fun inserir(drink: Cocktail) {
        viewModelScope.launch {
            cocktailDao.inserir(drink)
        }
    }

    fun atualizar(drink: Cocktail) {
        viewModelScope.launch {
            cocktailDao.atualizar(drink)

        }
    }

    fun deletar(id: Int) {
        viewModelScope.launch {
            cocktailDao.deletear(id)
        }
    }

}