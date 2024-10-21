package com.messapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.messapps.R
import com.messapps.data.model.NavigationData


class ChildAdapter
    (
    private var mShow: List<NavigationData>, private val clickCallback: CallbackChild
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mShowFilter: List<NavigationData> =
        mutableListOf()

    init {
        mShowFilter = mShow!!
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val order = mShowFilter!![position]
        (holder as ViewHolder)

        holder.tvMonth.text = order.name
        holder.tvMonth.setOnClickListener {
            clickCallback.viewDetailChild(order)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    interface CallbackChild {
        fun viewDetailChild(order: NavigationData)
    }

    override fun getItemCount(): Int {
        return if (mShowFilter == null) 0 else mShowFilter!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMonth: AppCompatTextView = itemView.findViewById(R.id.child_name)
    }
}