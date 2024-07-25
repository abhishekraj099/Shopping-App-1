package com.geniusapk.shopping.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.domain.models.UserData
import com.geniusapk.shopping.domain.models.UserDataParent
import com.geniusapk.shopping.domain.useCase.CreateUserUseCse
import com.geniusapk.shopping.domain.useCase.GetUserUseCase
import com.geniusapk.shopping.domain.useCase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(
    val createUserUseCse: CreateUserUseCse,
    val loginUserUseCase: LoginUserUseCase,
    val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _singUpScreenState = MutableStateFlow(SignUpScreenState())
    val singUpScreenState = _singUpScreenState.asStateFlow()


    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()


    fun createUser(userData: UserData) {
        viewModelScope.launch {
            createUserUseCse.createUser(userData).collect {
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