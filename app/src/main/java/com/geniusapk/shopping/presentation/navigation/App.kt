package com.geniusapk.shopping.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geniusapk.shopping.presentation.screens.SingUpScreenUi


@Composable
fun App(modifier: Modifier) {
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        NavHost(navController = navController, startDestination = Routes.SingUpScreen){

            composable<Routes.LoginScreen> {  }

            composable<Routes.SingUpScreen> {
                SingUpScreenUi()
            }

            composable<Routes.HomeScreen> {  }

            composable<Routes.ProfileScreen> {  }

            composable<Routes.WishListScreen> {  }

            composable<Routes.CartScreen> {  }

            composable<Routes.ProductDetailsScreen> {  }

            composable<Routes.CheckoutScreen> {  }



        }

    }
}