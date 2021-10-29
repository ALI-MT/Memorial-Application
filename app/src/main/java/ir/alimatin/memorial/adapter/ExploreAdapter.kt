package ir.alimatin.memorial.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ir.alimatin.memorial.R
import ir.alimatin.memorial.model.PostsModelItem
import ir.alimatin.memorial.retrofit.RetrofitClient
import ir.alimatin.memorial.view.TimeLineActivity
import kotlinx.android.synthetic.main.item_explore.view.*


class ExploreAdapter(private val contInst: Context) :
    RecyclerView.Adapter<ExploreAdapter.CustomViewHolder>() {
    private var list: List<PostsModelItem>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<PostsModelItem>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<PostsModelItem>) {
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
        private val img = view.ivImage
        fun setData(data: PostsModelItem) {
            val options = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
            Glide.with(contInst)
                .load(RetrofitClient.ImageMainServer + data.medias)
                .apply(options)
                .placeholder(R.drawable.bg_message_user)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img)

            cvItem.setOnClickListener {
                contInst.startActivity(Intent(contInst, TimeLineActivity::class.java))
            }
        }
    }

    override fun getItemCount() = list.size

}