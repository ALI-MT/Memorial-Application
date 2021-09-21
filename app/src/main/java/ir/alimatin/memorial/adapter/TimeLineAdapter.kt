package ir.alimatin.memorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import customView.SvgRatingBar
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataTimeLine
import kotlinx.android.synthetic.main.item_timeline.view.*


class TimeLineAdapter(private val contInst: Context) :
        RecyclerView.Adapter<TimeLineAdapter.CustomViewHolder>() {
    private var list: List<DataTimeLine>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataTimeLine>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataTimeLine>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TimeLineAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_timeline, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])
        holder.tvDot.setOnClickListener {
            showPopupMenu(it, position)
        }

    }

    private fun showPopupMenu(view: View, position: Int) {
        // inflate menu
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu_explorer, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener(position))
        popup.show()
    }

    internal class MyMenuItemClickListener(private val position: Int) : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.itemSave -> {
                    return true
                }

                R.id.itemReport -> {
                    return true
                }
            }
            return false
        }

    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ratingbar: SvgRatingBar = view.ratingbar
        val tvDot: TextView = view.tvDot
        fun setData(data: DataTimeLine) {
        }
    }

    override fun getItemCount() = list.size

}