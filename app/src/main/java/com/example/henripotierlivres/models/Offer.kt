package com.example.henripotierlivres.models

import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("offers")
    val offers: List<CommercialOffer>
)

