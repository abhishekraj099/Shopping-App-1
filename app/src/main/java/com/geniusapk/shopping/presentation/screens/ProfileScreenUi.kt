package com.geniusapk.shopping.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.geniusapk.shopping.domain.models.UserData
import com.geniusapk.shopping.domain.models.UserDataParent
import com.geniusapk.shopping.presentation.navigation.SubNavigation
import com.geniusapk.shopping.presentation.screens.utils.LogOutAlertDialog
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
    val upDateScreenState = viewModel.upDateScreenState.collectAsStateWithLifecycle()
    val userProfileImageState = viewModel.userProfileImageState.collectAsStateWithLifecycle()


    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }

    val isEdting = remember { mutableStateOf(false) }
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val imageUrl = remember { mutableStateOf("") }


    val firstName =
        remember { mutableStateOf(profileScreenState.value.userData?.userData?.fastName ?: "") }
    val lastName =
        remember { mutableStateOf(profileScreenState.value.userData?.userData?.lastName ?: "") }
    val email =
        remember { mutableStateOf(profileScreenState.value.userData?.userData?.email ?: "") }
    val phoneNumber =
        remember { mutableStateOf(profileScreenState.value.userData?.userData?.phoneNumber ?: "") }
    val address =
        remember { mutableStateOf(profileScreenState.value.userData?.userData?.address ?: "") }

    LaunchedEffect(profileScreenState.value.userData) {
        profileScreenState.value.userData?.userData?.let { userData ->
            firstName.value = userData.fastName ?: ""
            lastName.value = userData.lastName ?: ""
            email.value = userData.email ?: ""
            phoneNumber.value = userData.phoneNumber ?: ""
            address.value = userData.address ?: ""
        }
    }


    val pickMedia =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                //  viewModel.upDateUserData(uri)
                //uploadImageToDatabase(uri = uri) {
                //  imageUri.value = uri}
                viewModel.upLoadUserProfileImage(uri)
                imageUri.value = uri
            }
        }


    if (upDateScreenState.value.userData != null) {
        Toast.makeText(context, upDateScreenState.value.userData, Toast.LENGTH_SHORT).show()
    } else if (upDateScreenState.value.errorMessage != null) {
        Toast.makeText(context, upDateScreenState.value.errorMessage, Toast.LENGTH_SHORT).show()
    } else if (upDateScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }




    if (userProfileImageState.value.userData != null) {
        imageUrl.value = userProfileImageState.value.userData.toString()
    } else if (userProfileImageState.value.errorMessage != null) {
        Toast.makeText(context, userProfileImageState.value.errorMessage, Toast.LENGTH_SHORT).show()
    } else if (userProfileImageState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }







    if (profileScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (profileScreenState.value.errorMessage != null) {
        Text(text = profileScreenState.value.errorMessage!!)

    } else if (profileScreenState.value.userData != null) {

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ) {

// is staring we don,t have user iamge so we will show default image and  when user click on edit button then also user will se default image and if user select image then we will show that image then it will show user image

                if (isEdting.value == false) {

                    SubcomposeAsyncImage(
                        model = profileScreenState.value.userData!!.userData.profileImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .align(Alignment.Start),
                        loading = {

                        },
                        error = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    )
                } else {


                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        SubcomposeAsyncImage(
                            model = imageUri.value,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.Center)
                                .clip(CircleShape),
                            // .align(Alignment.Start),
                            loading = {

                            },
                            error = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        )
                        IconButton(
                            onClick = {
                                pickMedia.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White
                            )
                        }
                    }


                }



                Spacer(modifier = Modifier.size(16.dp))

                Row {
                    OutlinedTextField(
                        value = firstName.value,
                        modifier = Modifier.weight(1f),
                        readOnly = if (isEdting.value) false else true,

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = SweetPink,
                            focusedBorderColor = SweetPink
                        ),
                        shape = RoundedCornerShape(10.dp),

                        onValueChange = {
                            firstName.value = it

                        },
                        label = { Text("First Name") }
                    )
                    Spacer(modifier = Modifier.size(16.dp))


                    OutlinedTextField(
                        value = lastName.value,
                        modifier = Modifier.weight(1f),
                        readOnly = if (isEdting.value) false else true,

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = SweetPink,
                            focusedBorderColor = SweetPink
                        ),
                        onValueChange = {
                            lastName.value = it
                        },
                        shape = RoundedCornerShape(10.dp),

                        label = { Text("Last Name") }

                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    value = email.value,
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = if (isEdting.value) false else true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = SweetPink,
                        focusedBorderColor = SweetPink
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        email.value = it
                    },

                    label = { Text("Email") })

                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    value = phoneNumber.value,
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = if (isEdting.value) false else true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = SweetPink,
                        focusedBorderColor = SweetPink
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        phoneNumber.value = it
                    },
                    label = { Text("Phone Number") }

                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    value = address.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        address.value = it
                    },
                    readOnly = if (isEdting.value) false else true,

                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = SweetPink,
                        focusedBorderColor = SweetPink
                    ),

                    label = { Text("Address") }
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedButton(
                    onClick = {
                        showDialog.value = true


                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(SweetPink)
                ) {
                    Text("Log Out")
                }

                if (showDialog.value) {
                    LogOutAlertDialog(
                        onDismiss = {
                            showDialog.value = false
                        },
                        onConfirm = {
                            firebaseAuth.signOut()
                            navController.navigate(SubNavigation.LoginSingUpScreen)
                        }
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))

                if (isEdting.value == false) {
                    OutlinedButton(
                        onClick = {
                            isEdting.value = !isEdting.value

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),

                        ) {
                        Text("Edit Profile")
                    }
                } else {
                    OutlinedButton(
                        onClick = {


                            val updatedUserData = UserData(
                                fastName = firstName.value,
                                lastName = lastName.value,
                                email = email.value,
                                phoneNumber = phoneNumber.value,
                                address = address.value,
                                profileImage = imageUrl.value
                            )
                            val userDataParent = UserDataParent(
                                nodeId = profileScreenState.value.userData!!.nodeId,
                                userData = updatedUserData
                            )
                            viewModel.upDateUserData(userDataParent)
                            isEdting.value = !isEdting.value


                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),

                        ) {
                        Text("Save Profile")
                    }


                }


            }
        }



    }
}






