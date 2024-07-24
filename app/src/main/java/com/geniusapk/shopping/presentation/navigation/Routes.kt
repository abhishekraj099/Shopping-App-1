package com.geniusapk.shopping.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object LoginScreen

    @Serializable
    object SingUpScreen

    @Serializable
    object HomeScreen

    @Serializable
    object ProfileScreen

    @Serializable
    object WishListScreen

    @Serializable
    object CartScreen

    @Serializable
    object ProductDetailsScreen

    @Serializable
    object CheckoutScreen


}