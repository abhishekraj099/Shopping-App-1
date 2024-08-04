package com.geniusapk.shopping.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Vrpano
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.geniusapk.shopping.domain.models.ProductDataModels
import com.geniusapk.shopping.presentation.screens.EachProductDetalesScreenUi
import com.geniusapk.shopping.presentation.screens.GetAllFav
import com.geniusapk.shopping.presentation.screens.HomeScreenUi
import com.geniusapk.shopping.presentation.screens.LoginScreenUi
import com.geniusapk.shopping.presentation.screens.PayScreen
import com.geniusapk.shopping.presentation.screens.ProfileScreenUi
import com.geniusapk.shopping.presentation.screens.SingUpScreenUi
import com.geniusapk.shopping.presentation.screens.seeAllProducts
import com.google.firebase.auth.FirebaseAuth


@Composable
fun App(firebaseAuth: FirebaseAuth) {


    val navController = rememberNavController()

    var selected by remember { mutableIntStateOf(0) }

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, unCeldIcon = Icons.Outlined.Home),
        BottomNavItem("WishList", Icons.Default.Favorite, unCeldIcon = Icons.Outlined.Favorite),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, unCeldIcon = Icons.Outlined.ShoppingCart),
        BottomNavItem("Profile", Icons.Default.Person, unCeldIcon = Icons.Outlined.Person),

        )

    var startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSingUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route

                bottomNavItems.forEachIndexed { index, bottomNavItem ->
                    NavigationBarItem(
                        selected = selected == index,
                        onClick = {
                            selected = index
                            when (index) {
                                0 -> navController.navigate(Routes.HomeScreen)
                                1 -> navController.navigate(Routes.WishListScreen)
                                2 -> navController.navigate(Routes.CartScreen)
                                3 -> navController.navigate(Routes.ProfileScreen)
                            }
                        },
                        icon = {
                            Icon(
                                if (selected == index) bottomNavItem.icon else bottomNavItem.unCeldIcon,
                                contentDescription = bottomNavItem.name
                            )
                        },
                        label = { Text(bottomNavItem.name) }
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


            NavHost(navController = navController, startDestination = startScreen) {


                navigation<SubNavigation.LoginSingUpScreen>(startDestination = Routes.LoginScreen) {
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


                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {


                    composable<Routes.HomeScreen> {
                        HomeScreenUi(
                            navController = navController
                        )
                    }

                    composable<Routes.ProfileScreen> {
                        ProfileScreenUi(firebaseAuth = firebaseAuth, navController = navController)
                    }

                    composable<Routes.WishListScreen> {
                        GetAllFav(
                            navController = navController
                        )
                    }

                    composable<Routes.CartScreen> { }
                    composable<Routes.PayScreen> {
                        PayScreen()
                    }




                    composable<Routes.SeeAllProductsScreen> {
                        seeAllProducts()

                    }

                }







                composable<Routes.EachProductDetailsScreen> {
                    val product : Routes.EachProductDetailsScreen = it.toRoute()
                    EachProductDetalesScreenUi(
                        productID = product.productID,
                        navController = navController


                    )
                }

                composable<Routes.CheckoutScreen> { }


            }

        }

    }


}


data class BottomNavItem(val name: String, val icon: ImageVector, val unCeldIcon: ImageVector)
