package com.geniusapk.shopping.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.geniusapk.shopping.R
import com.geniusapk.shopping.presentation.navigation.SubNavigation
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel
import com.geniusapk.shopping.ui.theme.SweetPink
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    firebaseAuth: FirebaseAuth,
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        viewModel.getUserById(firebaseAuth.currentUser!!.uid)

    }
    val profileScreenState = viewModel.profileScreenState.collectAsStateWithLifecycle()



    if (profileScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (profileScreenState.value.errorMessage != null) {
        Text(text = profileScreenState.value.errorMessage!!)

    } else if (profileScreenState.value.userData != null) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Card() {
                SubcomposeAsyncImage(
                    model = "",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.Start),
                    loading = {

                    },
                    error = {
                        Text(text = "Error loading image")
                    }
                )

            }

            Spacer(modifier = Modifier.size(16.dp))

            Row {
                OutlinedTextField(
                    value = profileScreenState.value.userData!!.userData.fastName,
                    modifier = Modifier.weight(1f),
                    readOnly = true,

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = SweetPink,
                    ),
                    shape = RoundedCornerShape(10.dp),

                    onValueChange = {},
                    label = { Text("First Name") }
                )
                Spacer(modifier = Modifier.size(16.dp))


                OutlinedTextField(
                    value = profileScreenState.value.userData!!.userData.lastName,
                    modifier = Modifier.weight(1f),
                    readOnly = true,

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = SweetPink,
                    ),
                    onValueChange = {},
                    shape = RoundedCornerShape(10.dp),

                    label = { Text("Last Name") }

                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = profileScreenState.value.userData!!.userData.email,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = SweetPink,
                ),
                shape = RoundedCornerShape(10.dp),
                onValueChange = {},

                label = { Text("Email") })

            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = profileScreenState.value.userData!!.userData.phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = SweetPink,
                ),
                shape = RoundedCornerShape(10.dp),
                onValueChange = {},
                label = { Text("Phone Number") }

            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {},
                readOnly = true,

                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = SweetPink,
                ),

                label = { Text("Address") }
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedButton(
                onClick = {
                    firebaseAuth.signOut()
                    navController.navigate(SubNavigation.LoginSingUpScreen)

                },
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(SweetPink)
            ) {
                Text("Log Out")
            }
            Spacer(modifier = Modifier.size(16.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),

                ) {
                Text("Edit Profile")
            }


        }


    }
}






