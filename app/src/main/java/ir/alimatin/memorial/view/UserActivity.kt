package ir.alimatin.memorial.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.ExploreAdapter
import ir.alimatin.memorial.model.DataExplore
import kotlinx.android.synthetic.main.activity_explore.rvListExplore
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        contInst = this
        initExplore(getDataExplore())
        tvMessage.setOnClickListener {
            startActivity(Intent(contInst, ProfileActivity::class.java))
        }
    }


    private fun getDataExplore(): ArrayList<DataExplore> {
        val list = arrayListOf<DataExplore>()
        for (item in 1..30) {
            list.add(
                    DataExplore(
                            "link"
                            , "Ali.MT"
                            , 1
                    )
            )
        }
        return list
    }


    private fun initExplore(list: List<DataExplore>) {
        val spannedGridLayoutManager = SpannedGridLayoutManager(
                orientation = SpannedGridLayoutManager.Orientation.VERTICAL,
                spans = 3)

        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            if (position % 12 == 3 || position % 12 == 10) {
                SpanSize(2, 2)
            } else {
                SpanSize(1, 1)
            }
        }
        spannedGridLayoutManager.itemOrderIsStable = true
        rvListExplore.apply {
            layoutManager = spannedGridLayoutManager
            adapter = ExploreAdapter(context, list)
        }
    }

}