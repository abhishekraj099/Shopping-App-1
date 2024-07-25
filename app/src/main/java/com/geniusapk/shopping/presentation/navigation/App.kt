package com.geniusapk.shopping.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.geniusapk.shopping.presentation.screens.LoginScreenUi
import com.geniusapk.shopping.presentation.screens.ProfileScreenUi
import com.geniusapk.shopping.presentation.screens.SingUpScreenUi
import com.google.firebase.auth.FirebaseAuth


@Composable
fun App(firebaseAuth: FirebaseAuth) {



    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        var startScreen =  if (firebaseAuth.currentUser == null) {
            SubNavigation.LoginSingUpScreen
        } else {
            SubNavigation.MainHomeScreen
        }

        NavHost(navController = navController, startDestination = SubNavigation.LoginSingUpScreen){


            navigation<SubNavigation.LoginSingUpScreen>(startDestination = Routes.LoginScreen){
                composable<Routes.LoginScreen> {
                    LoginScreenUi(
                        navController = navController
                    )
                }

                composable<Routes.SingUpScreen> {
                    SingUpScreenUi(
                        navController = navController
                    )
                }
            }


            navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen){


                composable<Routes.HomeScreen> {
                    Text(text = "Home")
                }

                composable<Routes.ProfileScreen> {
                    ProfileScreenUi(firebaseAuth = firebaseAuth)
                }

                composable<Routes.WishListScreen> {  }

                composable<Routes.CartScreen> {  }

            }







            composable<Routes.ProductDetailsScreen> {  }

            composable<Routes.CheckoutScreen> {  }



        }

    }
}