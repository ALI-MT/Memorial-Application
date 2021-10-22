package ir.alimatin.memorial.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.alimatin.memorial.R
import ir.alimatin.memorial.common.DialogHandler
import kotlinx.android.synthetic.main.activity_receipt.ivBack
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        contInst = this
        onClick()
    }

    private fun onClick() {
        btn_buy_coin.setOnClickListener{
            DialogHandler().showDialogPaymentAmount(contInst)
        }

        ivBack.setOnClickListener {
            finish()
        }
        btn_receipt_coin.setOnClickListener{
            startActivity(Intent(contInst, ReceiptActivity::class.java))
        }
    }
}