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
) : Parcelable {
    fun getAddress(): String {
        return "${this.userAddress.street}, " +
                "${this.userAddress.suite}, " +
                "${this.userAddress.city}, " +
                this.userAddress.zipcode
    }
}


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

@Parcelize
@JsonClass(generateAdapter = true)
data class AlbumProperty(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class PhotoProperty(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String
) : Parcelable
