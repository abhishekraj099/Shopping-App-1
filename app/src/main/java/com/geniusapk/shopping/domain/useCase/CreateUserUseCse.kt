package com.geniusapk.shopping.domain.useCase

import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.domain.models.userData
import com.geniusapk.shopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCse @Inject constructor(private val repo: Repo) {
    fun createUser(userData: userData) : Flow<ResultState<String>> {
        return repo.registerUserWithEmailAndPassword(userData)

    }
}