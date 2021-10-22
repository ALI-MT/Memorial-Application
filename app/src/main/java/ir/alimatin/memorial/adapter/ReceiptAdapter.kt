package ir.alimatin.memorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataReceipt


class ReceiptAdapter(private val contInst: Context) :
        RecyclerView.Adapter<ReceiptAdapter.CustomViewHolder>() {
    private var list: List<DataReceipt>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataReceipt>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataReceipt>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ReceiptAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_receipt, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //val cvItem = view.cvItem
        fun setData(data: DataReceipt) {
        }
    }

    override fun getItemCount() = list.size

}