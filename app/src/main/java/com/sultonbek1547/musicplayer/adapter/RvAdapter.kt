package com.sultonbek1547.musicplayer.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sultonbek1547.musicplayer.databinding.RvItemBinding
import com.sultonbek1547.musicplayer.util.Constants.currentPosition
import com.sultonbek1547.musicplayer.util.Constants.musicDurationList

class RvAdapter(val context: Context, val list: ArrayList<String>, val rvClick: RvClick) :
    RecyclerView.Adapter<RvAdapter.Vh>() {


    inner class Vh(private val itemRvBinding: RvItemBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: String, position: Int) {
            itemRvBinding.tvName.text = user
            if (musicDurationList[position] / 1000 % 60 < 10)
                itemRvBinding.tvDuration.text =
                    "${musicDurationList[position] / 1000 / 60}:0${musicDurationList[position] / 1000 % 60}"
            else itemRvBinding.tvDuration.text =
                "${musicDurationList[position] / 1000 / 60}:${musicDurationList[position] / 1000 % 60}"

            if (position== currentPosition){
                rvClick.updateVariable(itemRvBinding.tvName)
            }






            itemRvBinding.root.setOnClickListener {
                rvClick.itemClicked(position, itemRvBinding.tvName, user)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(list[position], position)


    override fun getItemCount(): Int = list.size


}

interface RvClick {
    fun itemClicked(position: Int, textView: TextView, musicName: String)
    fun updateVariable(textView: TextView)
}