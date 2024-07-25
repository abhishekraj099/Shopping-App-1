package com.geniusapk.shopping.data.repo

import com.geniusapk.shopping.common.ResultState
import com.geniusapk.shopping.common.USER_COLLECTION
import com.geniusapk.shopping.domain.models.UserData
import com.geniusapk.shopping.domain.models.UserDataParent
import com.geniusapk.shopping.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    var firebaseAuth: FirebaseAuth,
    var firebaseFirestore: FirebaseFirestore
) : Repo {
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)


            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        firebaseFirestore.collection(USER_COLLECTION)
                            .document(it.result.user!!.uid.toString()).set(userData).addOnCompleteListener {
                                if (it.isSuccessful){
                                    trySend(ResultState.Success("User Registered Successfully and add to Firestore"))
                                }else{
                                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                                }
                            }

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

    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Logged In Successfully"))
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

    override fun getuserById(uid: String): Flow<ResultState<UserDataParent>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION)
            .document(uid).get().addOnCompleteListener{
                if (it.isSuccessful){
                    val data = it.result.toObject(UserData::class.java)
                    val userDataParent = UserDataParent(it.result.id, data!!)
                    trySend(ResultState.Success(userDataParent))
                }else{
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