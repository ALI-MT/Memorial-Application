package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.ContactAdapter
import ir.alimatin.memorial.model.DataContact
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_direct.ivBack
import kotlinx.android.synthetic.main.activity_timeline.rvList

class ContactActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        contInst = this
        initContact(getDataContact())

        configFabScroll()

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun configFabScroll() {
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val scrollY: Int = dy
                if (scrollY > 0) {
                    fab_contact.hide()
                } else {
                    fab_contact.show()
                }
            }
        })
    }

    private fun getDataContact(): ArrayList<DataContact> {
        val list = arrayListOf<DataContact>()
        for (item in 1..30) {
            list.add(
                    DataContact(
                            "link"
                            , "Ali.MT"
                            , 1
                    )
            )
        }
        return list
    }

    private fun initContact(list: List<DataContact>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = ContactAdapter(contInst, list)
        }
    }


}