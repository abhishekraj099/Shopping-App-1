package com.geniusapk.shopping.domain.repo

import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.domain.models.userData
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun registerUserWithEmailAndPassword(userData: userData):Flow<ResultState<String>>
}