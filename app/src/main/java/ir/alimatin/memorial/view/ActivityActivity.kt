package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.ActivityAdapter
import ir.alimatin.memorial.model.DataActivity
import kotlinx.android.synthetic.main.activity_activity.*
import kotlinx.android.synthetic.main.activity_timeline.rvList

class ActivityActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)
        contInst = this
        initi(getData())
        ivBack.setOnClickListener {
            finish()
        }
    }


    private fun getData(): ArrayList<DataActivity> {
        val list = arrayListOf<DataActivity>()
        for (item in 1..30) {
            list.add(
                    DataActivity(
                            "link"
                            , "Ali.MT"
                            , 1
                    )
            )
        }
        return list
    }

    private fun initi(list: List<DataActivity>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = ActivityAdapter(contInst, list)
        }
    }


}