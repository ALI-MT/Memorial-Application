package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.StoryAdapter
import ir.alimatin.memorial.model.DataStory
import kotlinx.android.synthetic.main.activity_timeline.*

class StoryActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        contInst = this
        onClick()
    }

    private fun initStory(list: List<DataStory>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = StoryAdapter(contInst, list)
        }
    }

    private fun onClick(){
        //Popup Menu
        val ivPopupMessage = findViewById<ImageView>(R.id.iv_more)
        ivPopupMessage.setOnClickListener{
            val popupMenu: PopupMenu = PopupMenu(this, ivPopupMessage)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_my_story,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.itemForward ->
                        Toast.makeText(this@StoryActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.itemShare ->
                        Toast.makeText(this@StoryActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.itemDelete ->
                        Toast.makeText(this@StoryActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                }
                true
            })
            popupMenu.show()
        }
    }
}