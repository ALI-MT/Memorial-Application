package ir.alimatin.memorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataActivity

class ActivityAdapter(private val contInst: Context) :
        RecyclerView.Adapter<ActivityAdapter.CustomViewHolder>() {
    private var list: List<DataActivity>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataActivity>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataActivity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ActivityAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_activity, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //val cvItem = view.cvItem
        fun setData(data: DataActivity) {
        }
    }

    override fun getItemCount() = list.size
}