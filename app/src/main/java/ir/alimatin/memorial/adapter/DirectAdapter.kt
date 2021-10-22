package ir.alimatin.memorial.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.view.MessageActivity
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataDirect
import ir.alimatin.memorial.simpleCamera.activities.CameraActivity
import kotlinx.android.synthetic.main.item_direct.view.*


class DirectAdapter(private val contInst: Context) :
        RecyclerView.Adapter<DirectAdapter.CustomViewHolder>() {
    private var list: List<DataDirect>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataDirect>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataDirect>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): DirectAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_direct, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])
    }

    private fun showPopupMenu(view: View, position: Int) {
        // inflate menu
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu_direct, popup.menu)
        popup.setOnMenuItemClickListener(TimeLineAdapter.MyMenuItemClickListener(position))
        popup.show()
    }

    internal class MyMenuItemClickListener(private val position: Int) : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.itemBlock -> {
                    return true
                }

                R.id.itemMute -> {
                    return true
                }
            }
            return false
        }
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val constraintLayout1 = view.constraintLayout1
        private val ivCamera: ImageView = view.ivCamera

        fun setData(data: DataDirect) {
            constraintLayout1.setOnClickListener {
                contInst.startActivity(Intent(contInst, MessageActivity::class.java))
            }

            ivCamera.setOnClickListener {
                //contInst.startActivity(Intent(contInst, StoryActivity::class.java))
                contInst.startActivity(Intent(contInst, CameraActivity::class.java))
                /*val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 2)*/
            }
        }
    }

    override fun getItemCount() = list.size
}