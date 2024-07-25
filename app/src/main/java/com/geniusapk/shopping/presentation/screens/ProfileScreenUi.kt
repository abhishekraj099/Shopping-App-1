package com.geniusapk.shopping.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    firebaseAuth: FirebaseAuth
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getUserById(firebaseAuth.currentUser!!.uid)

    }
}