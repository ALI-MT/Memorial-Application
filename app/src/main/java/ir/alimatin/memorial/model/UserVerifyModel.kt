package ir.alimatin.memorial.model

import java.io.Serializable

data class UserVerifyModel(val firstName: String? = null,
                           val name: String? = null,
                           val id: Int? = null) : Serializable