package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.ExploreAdapter
import ir.alimatin.memorial.adapter.StoryAdapter
import ir.alimatin.memorial.model.DataExplore
import ir.alimatin.memorial.model.DataStory
import kotlinx.android.synthetic.main.activity_explore.*
import kotlinx.android.synthetic.main.activity_explore.ivBack

class ExploreActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        contInst = this
        initStory(getDataStory())
        initExplore(getDataExplore())
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getDataStory(): ArrayList<DataStory> {
        val list = arrayListOf<DataStory>()
        for (item in 1..10) {
            list.add(
                    DataStory(
                            "link"
                            , "Ali.MT"
                            , 1
                    )
            )
        }
        return list
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

    private fun initStory(list: List<DataStory>) {
        rvListStory.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.HORIZONTAL, false)
            adapter = StoryAdapter(context, list)
        }
    }

    private fun initExplore(list: List<DataExplore>) {
        /*      val manager = SpannedGridLayoutManager(SpannedGridLayoutManager.GridSpanLookup { position -> // Conditions for 2x2 items
                  if (position % 12 ==1 || position % 12 == 6) {
                      SpannedGridLayoutManager.SpanInfo(2, 2)
                  } else {
                      SpannedGridLayoutManager.SpanInfo(1, 1)
                  }
              }, 3*//*column*//*, 1f*//*how big is default item*//*)*/
        val spannedGridLayoutManager = SpannedGridLayoutManager(
                orientation = SpannedGridLayoutManager.Orientation.VERTICAL,
                spans = 3)

        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            if (position % 12 == 1 || position % 12 == 6) {
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