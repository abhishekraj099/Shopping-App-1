package com.geniusapk.shopping.presentation.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.domain.models.CategoryDataModels
import com.geniusapk.shopping.domain.models.UserData
import com.geniusapk.shopping.domain.models.UserDataParent
import com.geniusapk.shopping.domain.useCase.CreateUserUseCase
import com.geniusapk.shopping.domain.useCase.GetUserUseCase
import com.geniusapk.shopping.domain.useCase.LoginUserUseCase
import com.geniusapk.shopping.domain.useCase.UpDateUserDataUseCase
import com.geniusapk.shopping.domain.useCase.getCategoryInLimit
import com.geniusapk.shopping.domain.useCase.userProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(
    val createUserUseCase: CreateUserUseCase,
    val loginUserUseCase: LoginUserUseCase,
    val getUserUseCase: GetUserUseCase,
    val upDateUserDataUseCase: UpDateUserDataUseCase,
    val userProfileImageUseCase: userProfileImageUseCase,
    val getCategoryInLimit: getCategoryInLimit
) : ViewModel() {

    private val _singUpScreenState = MutableStateFlow(SignUpScreenState())
    val singUpScreenState = _singUpScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _upDateScreenState = MutableStateFlow(UpDateScreenState())
    val upDateScreenState = _upDateScreenState.asStateFlow()

    private val _userProfileImageState = MutableStateFlow(uploadUserProfileImageState())
    val userProfileImageState = _userProfileImageState.asStateFlow()


    private val _categoryInLimitScreenState = MutableStateFlow(CategoryScreenState())
    val categoryInLimitScreenState = _categoryInLimitScreenState.asStateFlow()


    fun getCategoriesInLimited() {
        viewModelScope.launch {
            getCategoryInLimit.getCategoriesInLimited().collect {
                when(it){
                    is ResultState.Error -> {
                        _categoryInLimitScreenState.value = _categoryInLimitScreenState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _categoryInLimitScreenState.value = _categoryInLimitScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _categoryInLimitScreenState.value = _categoryInLimitScreenState.value.copy(
                            isLoading = false,
                            categories = it.data
                        )
                    }
                }
            }
        }
    }


    fun upLoadUserProfileImage(uri: Uri) {
        viewModelScope.launch {
            userProfileImageUseCase.userProfileImage(uri).collect {
                when (it) {
                    is ResultState.Error -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }


        fun upDateUserData(
            userDataParent: UserDataParent
        ) {
            viewModelScope.launch {
                upDateUserDataUseCase.upDateUserData(userDataParent = userDataParent).collect {
                    when (it) {
                        is ResultState.Error -> {
                            _upDateScreenState.value = _upDateScreenState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                        }

                        is ResultState.Loading -> {
                            _upDateScreenState.value = _upDateScreenState.value.copy(
                                isLoading = true
                            )
                        }

                        is ResultState.Success -> {
                            _upDateScreenState.value = _upDateScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )

                        }
                    }
                }
            }
        }


        fun createUser(userData: UserData) {
            viewModelScope.launch {
                createUserUseCase.createUser(userData).collect {
                    when (it) {
                        is ResultState.Error -> {
                            _singUpScreenState.value = _singUpScreenState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                        }

                        ResultState.Loading -> {
                            _singUpScreenState.value = _singUpScreenState.value.copy(
                                isLoading = true
                            )
                        }

                        is ResultState.Success -> {
                            _singUpScreenState.value = _singUpScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                        }
                    }
                }

            }
        }


        fun loginUser(userData: UserData) {
            viewModelScope.launch {
                loginUserUseCase.loginUser(userData).collect {
                    when (it) {
                        is ResultState.Error -> {
                            _loginScreenState.value = _loginScreenState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                        }

                        ResultState.Loading -> {
                            _loginScreenState.value = _loginScreenState.value.copy(
                                isLoading = true
                            )
                        }

                        is ResultState.Success -> {
                            _loginScreenState.value = _loginScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                        }
                    }
                }

            }
        }


        fun getUserById(uid: String) {
            viewModelScope.launch {
                getUserUseCase.getuserById(uid).collectLatest {
                    when (it) {
                        is ResultState.Error -> {
                            _profileScreenState.value = _profileScreenState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )

                        }

                        ResultState.Loading -> {
                            _profileScreenState.value = _profileScreenState.value.copy(
                                isLoading = true
                            )
                        }

                        is ResultState.Success -> {
                            _profileScreenState.value = _profileScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                        }
                    }
                }
            }
        }


    }

    data class ProfileScreenState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val userData: UserDataParent? = null
    )


    data class SignUpScreenState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val userData: String? = null

    )

    data class LoginScreenState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val userData: String? = null
    )

    data class UpDateScreenState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val userData: String? = null
    )

    data class uploadUserProfileImageState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val userData: String? = null
    )


data class CategoryScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val categories: List<CategoryDataModels>? = null

)