package com.geniusapk.shopping.domain.models




data class UserData (
    val name : String,
    val email : String,
    val password : String,
    val phoneNumber : String,

)


data class UserDataParent(val nodeId : String, val userData: UserData)