package ir.alimatin.memorial.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.MessageAdapter
import ir.alimatin.memorial.common.DialogHandler
import ir.alimatin.memorial.model.DataMessage
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_timeline.rvList

class MessageActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        contInst = this
        initi(getData())
        onClick()
    }

    private fun onClick() {
        tvUser.setOnClickListener {
            startActivity(Intent(contInst, ProfileActivity::class.java))
        }
        ivBack.setOnClickListener {
            finish()
        }
        ivAttachment.setOnClickListener{
            DialogHandler().showDialogDotDirect(contInst)
        }

        //Popup Menu
        val ivPopupMessage = findViewById<ImageButton>(R.id.ivPopupMessage)
        ivPopupMessage.setOnClickListener{
            val popupMenu: PopupMenu = PopupMenu(this, ivPopupMessage)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_direct,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.itemBlock ->
                        Toast.makeText(this@MessageActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.itemMute ->
                        Toast.makeText(this@MessageActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                }
                true
            })
            popupMenu.show()
        }
    }


    private fun getData(): ArrayList<DataMessage> {
        val list = arrayListOf<DataMessage>()
        for (item in 1..30) {
            list.add(
                    DataMessage(
                            "link"
                            , "Ali.MT"
                            , 1
                    )
            )
        }
        return list
    }

    private fun initi(list: List<DataMessage>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = MessageAdapter(contInst, list)
        }
    }
}