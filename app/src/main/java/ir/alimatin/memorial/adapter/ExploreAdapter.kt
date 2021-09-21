package ir.alimatin.memorial.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.view.TimeLineActivity
import ir.alimatin.memorial.model.DataExplore
import kotlinx.android.synthetic.main.item_explore.view.*


class ExploreAdapter(private val contInst: Context) :
        RecyclerView.Adapter<ExploreAdapter.CustomViewHolder>() {
    private var list: List<DataExplore>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataExplore>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataExplore>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ExploreAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_explore, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])


    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val cvItem = view.cvItem
        fun setData(data: DataExplore) {
            cvItem.setOnClickListener {
                contInst.startActivity(Intent(contInst, TimeLineActivity::class.java))
            }
        }
    }

    override fun getItemCount() = list.size

}