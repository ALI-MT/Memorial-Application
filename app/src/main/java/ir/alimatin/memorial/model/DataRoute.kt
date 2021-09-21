package ir.alimatin.memorial.model

import java.io.Serializable

class DataRoute(val user_name: String? = null,
                val tv_date: String? = null,
                val tv_time: String? = null,
                val tv_duration: String? = null,
                val tv_distance: String? = null,
                val tv_amount_time: String? = null,
                val tv_amount_duration: String? = null,
                val tv_amount_distance: String) : Serializable