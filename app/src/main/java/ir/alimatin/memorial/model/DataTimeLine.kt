package ir.alimatin.memorial.model

import java.io.Serializable

data class DataTimeLine(val image: String? = null,
                        val name: String? = null,
                        val id: Int? = null) : Serializable