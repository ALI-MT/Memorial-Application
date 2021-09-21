package ir.alimatin.memorial.model

import java.io.Serializable

class DataReceipt(val payment_amount: String? = null,
                  val tracking_code: String? = null,
                  val date: String? = null,
                  val clock: String? = null,
                  val condition: String? = null,
                  val tv_pay_amount: String? = null,
                  val tv_pay_tracking_code: String? = null,
                  val tv_pay_date: String? = null,
                  val tv_pay_time: String? = null,
                  val tv_pay_condition: String) : Serializable