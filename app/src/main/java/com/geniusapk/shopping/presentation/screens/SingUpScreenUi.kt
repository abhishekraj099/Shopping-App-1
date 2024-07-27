package com.geniusapk.shopping.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.geniusapk.shopping.domain.models.UserData
import com.geniusapk.shopping.presentation.navigation.Routes
import com.geniusapk.shopping.presentation.navigation.SubNavigation
import com.geniusapk.shopping.presentation.screens.utils.CustomTextField
import com.geniusapk.shopping.presentation.screens.utils.SuccessAlertDialog
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel
import com.geniusapk.shopping.ui.theme.SweetPink


@Composable
fun SingUpScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController

) {


    val state = viewModel.singUpScreenState.collectAsStateWithLifecycle()

    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (state.value.errorMessage != null) {

        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = state.value.errorMessage!!)

        }
    }else if (state.value.userData != null){
        SuccessAlertDialog(
            onClick = {
                navController.navigate(SubNavigation.MainHomeScreen)
            }
        )

    }

    else {


        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
       // var fullName = remember { mutableStateOf("$firstName $lastName") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Signup",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            CustomTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = "First Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = Icons.Default.Person,


                )

            CustomTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = "Last Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = Icons.Default.Person,

                )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = Icons.Default.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
            CustomTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone Number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = Icons.Default.Phone,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )



            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Create Password",
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = Icons.Default.Lock,
            )

            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = Icons.Default.Lock,
            )

            Button(
                onClick = {
                    val userData = UserData(


                        fastName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password,
                        phoneNumber = phoneNumber
                    )
                    viewModel.createUser(
                        userData
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(SweetPink)
            ) {
                Text("Signup", color = Color.White)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account?")
                TextButton(onClick = {
                    navController.navigate(Routes.LoginScreen)
                }) {
                    Text("Login", color = SweetPink)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("OR", modifier = Modifier.padding(horizontal = 8.dp))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }



            OutlinedButton(
                onClick = { /* Handle Google login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Log in with Google")
            }
        }
    }
}