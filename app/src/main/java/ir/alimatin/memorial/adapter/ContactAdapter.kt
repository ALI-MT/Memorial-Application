package ir.alimatin.memorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.DataContact
import kotlinx.android.synthetic.main.item_contacts.view.*


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

        val tv_userName = view.tv_userName
        val tvState = view.tvState
        val ivUser = view.ivUser
        val first_name = view.first_name
        fun setData(data: DataContact) {
            tv_userName.text = data.name
            tvState.text = data.number.toString()
            if (data.image == null)
                first_name.text = data.name?.substring(0, 1)
            else
                first_name.visibility = View.GONE

            val options = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
            Glide.with(contInst)
                .load(data.image)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivUser)
        }
    }

    override fun getItemCount() = list.size

}