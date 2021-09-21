package ir.alimatin.memorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataMessage


class MessageAdapter(private val contInst: Context) :
        RecyclerView.Adapter<MessageAdapter.CustomViewHolder>() {
    private var list: List<DataMessage>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataMessage>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataMessage>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MessageAdapter.CustomViewHolder {
        if (viewType == 1) {
            return CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_message_user, parent, false)
            )
        } else {
            return CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_message_me, parent, false)
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            1
        } else {
            2
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //val cvItem = view.cvItem
        fun setData(data: DataMessage) {
        }
    }

    override fun getItemCount() = list.size
}