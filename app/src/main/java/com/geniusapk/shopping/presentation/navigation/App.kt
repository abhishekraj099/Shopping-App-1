package com.geniusapk.shopping.presentation.navigation

import AllCategoriesScreenUi
import GetAllFav
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
import com.geniusapk.shopping.presentation.screens.CartScreenUi
import com.geniusapk.shopping.presentation.screens.CheckOutScreenUi
import com.geniusapk.shopping.presentation.screens.EachProductDetailsScreenUi
import com.geniusapk.shopping.presentation.screens.HomeScreenUi
import com.geniusapk.shopping.presentation.screens.LoginScreenUi
import com.geniusapk.shopping.presentation.screens.PayScreen
import com.geniusapk.shopping.presentation.screens.ProfileScreenUi
import com.geniusapk.shopping.presentation.screens.SingUpScreenUi
import com.geniusapk.shopping.presentation.screens.GetAllProducts
import com.google.firebase.auth.FirebaseAuth


@Composable
fun App(firebaseAuth: FirebaseAuth ,  payTest : () -> Unit) {


    val navController = rememberNavController()

    var selected by remember { mutableIntStateOf(0) }

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, unseletedIcon = Icons.Outlined.Home),
        BottomNavItem("WishList", Icons.Default.Favorite, unseletedIcon = Icons.Outlined.Favorite),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, unseletedIcon = Icons.Outlined.ShoppingCart),
        BottomNavItem("Profile", Icons.Default.Person, unseletedIcon = Icons.Outlined.Person),

        )

    var startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSingUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    Scaffold(
        Modifier.fillMaxSize(),
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
                                if (selected == index) bottomNavItem.icon else bottomNavItem.unseletedIcon,
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

                    composable<Routes.CartScreen> {
                        CartScreenUi(
                            navController = navController
                        )
                    }
                    composable<Routes.PayScreen> {
                        PayScreen()
                    }




                    composable<Routes.SeeAllProductsScreen> {
                        GetAllProducts(
                            navController = navController
                        )

                    }

                    composable<Routes.AllCategoriesScreen> {
                        AllCategoriesScreenUi(
                            navController = navController
                        )


                    }

                }







                composable<Routes.EachProductDetailsScreen> {
                    val product: Routes.EachProductDetailsScreen = it.toRoute()
                    EachProductDetailsScreenUi(
                        productID = product.productID,
                        navController = navController


                    )
                }

                composable<Routes.CheckoutScreen> {
                    val product: Routes.EachProductDetailsScreen = it.toRoute()
                    CheckOutScreenUi(
                        productID = product.productID,
                        navController = navController ,
                        pay = payTest)

                }


            }

        }

    }


}


data class BottomNavItem(val name: String, val icon: ImageVector, val unseletedIcon: ImageVector)
