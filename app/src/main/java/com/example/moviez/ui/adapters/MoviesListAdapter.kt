package com.example.moviez.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviez.R
import com.example.moviez.data.models.MoviesItem
import com.example.moviez.databinding.MoviesItemCellBinding
import com.squareup.picasso.Picasso


class MoviesListAdapter(var mMoviesItemList: MutableList<MoviesItem>) : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    var currentPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: MoviesItemCellBinding = DataBindingUtil.inflate(inflater, R.layout.movies_item_cell, parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mMoviesItemList = mMoviesItemList[position]
        holder.bind(mMoviesItemList)
        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }
    }

    override fun getItemCount(): Int {
        return mMoviesItemList.size
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
    }

    val searchResultsItem: List<MoviesItem>
        get() = mMoviesItemList

    fun addChatMassgeModel(mMoviesItemList: MoviesItem) {
        try {
            this.mMoviesItemList.add(mMoviesItemList)
            notifyItemInserted(itemCount)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSearchResultsItemList(mMoviesItemList: MutableList<MoviesItem>) {
        this.mMoviesItemList = mMoviesItemList
        notifyDataSetChanged()
    }

    fun setItems(items: List<MoviesItem>) {
        val startPosition = this.mMoviesItemList.size
        this.mMoviesItemList.addAll(items)
        notifyItemRangeChanged(startPosition, items.size)
    }

    private fun dataSetChanged() {
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(view: View, private val binding: MoviesItemCellBinding) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View) {
            try {
                if (onItemClickListener != null) {
//                    currentPos = adapterPosition
//                    notifyItemRangeChanged(0, mMoviesItemList.size)
                    onItemClickListener!!.onItemClick(v, adapterPosition)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @UiThread
        fun bind(mMoviesItemList: MoviesItem?) {
            binding.moviesItem = mMoviesItemList
            val imageUrl = mMoviesItemList?.getFormattedPosterPath()
            Picasso.get().load(imageUrl).into(binding.ivPoster)
        }

        init {
            view.setOnClickListener(this)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun deleteitem(position: Int) {
        mMoviesItemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mMoviesItemList.size)
    }

    fun clearAdapter() {
        val size = mMoviesItemList.size
        mMoviesItemList.clear()
        notifyItemRangeRemoved(0, size)
    }
}