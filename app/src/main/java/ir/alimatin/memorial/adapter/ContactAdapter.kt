package ir.alimatin.memorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataContact


class ContactAdapter(private val contInst: Context) :
        RecyclerView.Adapter<ContactAdapter.CustomViewHolder>() {
    private var list: List<DataContact>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataContact>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataContact>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ContactAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_contacts, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])


    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //val cvItem = view.cvItem
        fun setData(data: DataContact) {

        }
    }

    override fun getItemCount() = list.size

}