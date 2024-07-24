package com.geniusapk.shopping.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.domain.models.userData
import com.geniusapk.shopping.domain.useCase.CreateUserUseCse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor( val createUserUseCse: CreateUserUseCse) : ViewModel() {

    val _singUpScreenState = MutableStateFlow(SignUpScreenState())
    val singUpScreenState = _singUpScreenState.asStateFlow()


    fun createUser(userData: userData) {
        viewModelScope.launch {
            createUserUseCse.createUser(userData).collect {
                when(it){
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

}


data class SignUpScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null

)