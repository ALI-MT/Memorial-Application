package ir.alimatin.memorial.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.view.StoryActivity
import ir.alimatin.memorial.model.DataStory
import kotlinx.android.synthetic.main.item_story.view.*

class StoryAdapter(private val contInst: Context) :
        RecyclerView.Adapter<StoryAdapter.CustomViewHolder>() {
    private var list: List<DataStory>

    init {
        list = ArrayList()
    }

    constructor(contInst: Context, list: List<DataStory>) : this(contInst) {
        this.list = list
    }

    fun setData(list: List<DataStory>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): StoryAdapter.CustomViewHolder =
            CustomViewHolder(
                    LayoutInflater.from(contInst).inflate(R.layout.item_story, parent, false)
            )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.setData(list[position])


    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvText: TextView = view.tvText
        private val ivImage = view.ivImage

        //val cvItem = view.cvItem
        fun setData(data: DataStory) {
            tvText.text = data.name
            ivImage.setOnClickListener {
                contInst.startActivity(Intent(contInst, StoryActivity::class.java))
            }
        }
    }

    override fun getItemCount() = list.size

}