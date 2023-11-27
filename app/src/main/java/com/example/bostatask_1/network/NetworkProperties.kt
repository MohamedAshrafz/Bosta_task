package com.example.bostatask_1.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserProperty(
    @Json(name = "id")
    val userId: String,
    @Json(name = "name")
    val userName: String,
    @Json(name = "address")
    val userAddress: AddressProperty,
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class AddressProperty(
    @Json(name = "street")
    val street: String,
    @Json(name = "suite")
    val suite: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "zipcode")
    val zipcode: String
) : Parcelable
