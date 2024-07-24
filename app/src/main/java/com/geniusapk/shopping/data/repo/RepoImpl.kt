package com.geniusapk.shopping.data.repo

import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.domain.models.userData
import com.geniusapk.shopping.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(var firebaseAuth: FirebaseAuth) : Repo {
    override fun registerUserWithEmailAndPassword(userData: userData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)


            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Registered Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }


                }
            awaitClose {
                close()
            }

        }
}