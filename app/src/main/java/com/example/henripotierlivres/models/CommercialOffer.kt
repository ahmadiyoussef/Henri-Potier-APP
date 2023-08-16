package com.example.henripotierlivres.models

import com.google.gson.annotations.SerializedName

data class CommercialOffer (
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: Int,
    @SerializedName("sliceValue")
    val sliceValue: Int? = null
        )