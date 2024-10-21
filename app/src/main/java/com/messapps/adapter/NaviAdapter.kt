package com.messapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.messapps.adapter.ChildAdapter.CallbackChild
import com.messapps.R
import com.messapps.data.model.NavigationData


class NaviAdapter
    (
    private var mCon: Context,
    private var mShowFilter: MutableList<NavigationData>,
    private var expandableListDetail: HashMap<String, List<NavigationData>>,
    private val clickCallback: ClickCallback, private val clickChild: CallbackChild
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastPosOpen: Int = -1

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_group, viewGroup, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val order = mShowFilter!![position]
        (holder as ViewHolder)

        holder.tv_name.text = buildString {
            append(order.name!!)
        }

        if (expandableListDetail[order.name] != null) {

            val layoutManager =
                LinearLayoutManager(mCon, LinearLayoutManager.VERTICAL, false)
            holder.recyclerView.layoutManager = layoutManager
            val mAdapter = ChildAdapter(expandableListDetail[order.name]!!, clickChild)
            holder.recyclerView.adapter = mAdapter

        }

        holder.tv_name!!.setOnClickListener {
            if (position == 3 || position == 4) {
                clickCallback.callNaviFragment(position,order)
            } else {
                if (expandableListDetail[order.name]!!.isNotEmpty()) {
                    updatePos(true, position)
                } else {
                    clickCallback.callNaviFragment(position,order)
                }
            }
        }
        if (position == lastPosOpen) {
            holder.ll_list.visibility = View.VISIBLE
        } else {
            holder.ll_list.visibility = View.GONE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    fun updatePos(boolean: Boolean, pos: Int) {

        if (lastPosOpen != -1) {
            mShowFilter[lastPosOpen].boolean = false
            val mcheck = lastPosOpen
            lastPosOpen = -1
            notifyItemChanged(mcheck)

            if (pos != mcheck) {
                lastPosOpen = pos
                mShowFilter[pos].boolean = true
                notifyItemChanged(pos)
            }

        } else {
            lastPosOpen = pos
            mShowFilter[pos].boolean = true
            notifyItemChanged(pos)
        }


    }

    interface ClickCallback {
        fun callNaviFragment(pos: Int?,order: NavigationData)
    }

    override fun getItemCount(): Int {
        return if (mShowFilter == null) 0 else mShowFilter!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name: AppCompatTextView = itemView.findViewById(R.id.title)
        var ll_list: LinearLayoutCompat = itemView.findViewById(R.id.ll_list)
        var recyclerView: RecyclerView = itemView.findViewById(R.id.month_list)
    }
}