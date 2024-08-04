package com.geniusapk.shopping.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.geniusapk.shopping.domain.models.ProductDataModels
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel


@Composable
fun EachProductDetalesScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,
    productID: String,
) {
    val addToCartState = viewModel.addToCartState.collectAsStateWithLifecycle()

    val getProductById = viewModel.getProductByIDState.collectAsStateWithLifecycle()

    val addToFav = viewModel.addtoFavState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        viewModel.getProductByID(productID)

    }


    if (getProductById.value.isLoading){
        Box (
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()

        }
    }else if(getProductById.value.errorMessage != null){
        Text(text = getProductById.value.errorMessage!!)
    }else if(getProductById.value.userData != null){
        Text(text = getProductById.value.userData!!.productId)

          getProductById.value.userData!!.productId = productID

        val productDataModels = ProductDataModels(
            productId = getProductById.value.userData!!.productId,
            name = getProductById.value.userData!!.name,
            description = getProductById.value.userData!!.description,
            price = getProductById.value.userData!!.price,
            image = getProductById.value.userData!!.image,
            category = getProductById.value.userData!!.category,

            )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        AsyncImage(
            model = getProductById.value.userData?.image,
            contentDescription = null
        )

        Text(
            text = getProductById.value.userData!!.name,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = getProductById.value.userData!!.description,
        )
        Text(
            text = getProductById.value.userData!!.price,
        )

        Button(
            onClick = {
                    viewModel.addToCart(productDataModels = productDataModels)



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
            onClick = {

                viewModel.addToFav(productDataModels = productDataModels)

            },
        ){
            Text(text = "Add to fav")
        }
    }
    }
}