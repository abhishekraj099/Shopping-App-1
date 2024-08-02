package com.geniusapk.shopping.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.geniusapk.shopping.domain.models.ProductDataModels
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel

@Composable
fun EachProductDetaleScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,

    product : ProductDataModels
){
    val addToCartState = viewModel.addToCartState.collectAsStateWithLifecycle()


    if (addToCartState.value.isLoading){
        CircularProgressIndicator()
    }else if(addToCartState.value.errorMessage != null){
        Text(text = addToCartState.value.errorMessage!!)
    }else if(addToCartState.value.userData != null){
        Text(text = addToCartState.value.userData!!)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        AsyncImage(
            model = product.image,
            contentDescription = null
        )

        Text(
            text = product.name,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = product.description,
        )
        Text(
            text = product.price,
        )

        Button(
            onClick = {

            },
        ){
            Text(text = "Add to Cart")
        }
        Button(
            onClick = { /*TODO*/ },
        ){
            Text(text = "buy now")
        }
        Button(
            onClick = { /*TODO*/ },
        ){
            Text(text = "Add to fav")
        }
    }
}