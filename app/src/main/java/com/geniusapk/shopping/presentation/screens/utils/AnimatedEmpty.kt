package com.geniusapk.shopping.presentation.screens.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.geniusapk.shopping.R


@Composable
fun AnimatedEmpty() {
    val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gaustempty))
    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {


        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp),


            )

        Text(
            text = "No Products Found",
            modifier = Modifier
                .fillMaxWidth()
        )


    }
}