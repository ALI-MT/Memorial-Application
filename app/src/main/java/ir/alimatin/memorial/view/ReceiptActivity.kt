package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.ReceiptAdapter
import ir.alimatin.memorial.model.DataReceipt
import kotlinx.android.synthetic.main.activity_new_post.ivBack
import kotlinx.android.synthetic.main.activity_receipt.*

class ReceiptActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
        contInst = this
        initi(getData())
        onClick()
    }

    private fun onClick() {
        btn_ok.setOnClickListener{
            finish()
        }

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getData(): ArrayList<DataReceipt> {
        val list = arrayListOf<DataReceipt>()
        for (item in 1..30) {
            list.add(
                    DataReceipt(
                            "link"
                            ,"link"
                            ,"link"
                            ,"link"
                            ,"link"
                            ,"link"
                            ,"link"
                            ,"link"
                            ,"link"
                            ,"link"
                    )
            )
        }
        return list
    }

    private fun initi(list: List<DataReceipt>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = ReceiptAdapter(contInst, list)
        }
    }
}