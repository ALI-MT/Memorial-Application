package ir.alimatin.memorial.model

import java.io.Serializable

data class DataContact(val image: String? = null,
                       val name: String? = null,
                       val number: String? = null,
                       val id: Int? = null) : Serializable