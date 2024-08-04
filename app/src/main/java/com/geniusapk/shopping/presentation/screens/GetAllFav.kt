package com.geniusapk.shopping.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.geniusapk.shopping.domain.models.ProductDataModels
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel


@Composable
fun GetAllFav(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,
) {
    val getAllFav = viewModel.getAllFavState.collectAsStateWithLifecycle()

    val getFavData: List<ProductDataModels?> = getAllFav.value.userData ?: emptyList()


    LaunchedEffect(key1 = Unit){
        viewModel.getAllFav()

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "All Fav")

        LazyColumn {
            items(
                getFavData
            ){
                Text(text = it!!.name)


            }

        }

    }




}