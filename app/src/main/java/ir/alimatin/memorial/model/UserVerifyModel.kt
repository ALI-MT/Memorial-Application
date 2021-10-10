package ir.alimatin.memorial.model

import java.io.Serializable

data class UserVerifyModel(
    val status: Int? = null,
    val _id: String? = null,
    val code: String? = null,
    val dateCreated: String? = null,
    val __v: String? = null
) : Serializable