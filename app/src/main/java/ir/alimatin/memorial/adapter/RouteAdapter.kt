package ir.alimatin.memorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataRoute


class RouteAdapter(private val contInst: Context) :
        RecyclerView.Adapter<RouteAdapter.CustomViewHolder>() {
    private var list: List<DataRoute>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataRoute>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataRoute>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RouteAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_route, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //val cvItem = view.cvItem
        fun setData(data: DataRoute) {
        }
    }

    override fun getItemCount() = list.size

}