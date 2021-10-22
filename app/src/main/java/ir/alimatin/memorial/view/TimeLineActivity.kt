package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.TimeLineAdapter
import ir.alimatin.memorial.model.DataTimeLine
import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.activity_timeline.rvList

class TimeLineActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        contInst = this
        initTimeLine(getDataTimeLine())
        ivBack.setOnClickListener {
            finish()
        }
    }


    private fun getDataTimeLine(): ArrayList<DataTimeLine> {
        val list = arrayListOf<DataTimeLine>()
        for (item in 1..30) {
            list.add(
                    DataTimeLine(
                            "link"
                            , "Ali.MT"
                            , 1
                    )
            )
        }
        return list
    }

    private fun initTimeLine(list: List<DataTimeLine>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = TimeLineAdapter(contInst, list)
        }
    }


}