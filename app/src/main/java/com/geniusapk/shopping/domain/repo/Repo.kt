package com.geniusapk.shopping.domain.repo

import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.domain.models.UserData
import com.geniusapk.shopping.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun registerUserWithEmailAndPassword(userData: UserData):Flow<ResultState<String>>
    fun loginUserWithEmailAndPassword(userData: UserData):Flow<ResultState<String>>
    fun getuserById(uid:String): Flow<ResultState<UserDataParent>>

}