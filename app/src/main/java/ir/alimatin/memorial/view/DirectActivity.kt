package ir.alimatin.memorial.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.DirectAdapter
import ir.alimatin.memorial.model.DataDirect
import kotlinx.android.synthetic.main.activity_direct.*
import kotlinx.android.synthetic.main.activity_direct.ivBack
import kotlinx.android.synthetic.main.activity_timeline.rvList

class DirectActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direct)
        contInst = this
        initDirect(getDataDirect())

        configFabScroll()
        onClick()
    }

    private fun configFabScroll() {
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val scrollY: Int = dy
                if (scrollY > 0) {
                    fab_direct.hide()
                } else {
                    fab_direct.show()
                }
            }
        })
    }

    private fun getDataDirect(): ArrayList<DataDirect> {
        val list = arrayListOf<DataDirect>()
        for (item in 1..30) {
            list.add(
                    DataDirect(
                            "link"
                            , "Ali.MT"
                            , 1
                    )
            )
        }
        return list
    }

    private fun onClick() {
        fab_direct.setOnClickListener{
            startActivity(Intent(contInst, ContactActivity::class.java))
        }

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initDirect(list: List<DataDirect>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = DirectAdapter(contInst, list)
        }
    }


}