package ir.alimatin.memorial.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.RouteAdapter
import ir.alimatin.memorial.model.DataRoute
import kotlinx.android.synthetic.main.activity_new_post.ivBack
import kotlinx.android.synthetic.main.activity_receipt.*

class RouteActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)
        contInst = this
        initi(getData())
        onClick()
    }

    private fun onClick() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getData(): ArrayList<DataRoute> {
        val list = arrayListOf<DataRoute>()
        for (item in 1..30) {
            list.add(
                    DataRoute(
                            "link"
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

    private fun initi(list: List<DataRoute>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = RouteAdapter(contInst, list)
        }
    }
}