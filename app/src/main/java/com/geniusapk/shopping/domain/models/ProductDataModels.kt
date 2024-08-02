package com.geniusapk.shopping.domain.models

data class ProductDataModels(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val image: String = "",
    val date: Long = System.currentTimeMillis(),
    val createBy: String = "",
    val availableUints : Int = 0,

)
