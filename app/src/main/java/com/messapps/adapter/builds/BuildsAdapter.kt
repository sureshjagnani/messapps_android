package com.messapps.adapter.builds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.messapps.databinding.BuildItemBinding
import com.messapps.data.model.invoices.InvoicesData



class BuildsAdapter
    (
    private var mShow: List<InvoicesData>, private val clickCallback: CallbackDetail
) : RecyclerView.Adapter<BuildsAdapter.ViewHolder>() {

    private var mShowFilter: List<InvoicesData> =
        mutableListOf()
    private val mView = 0
    private val mDownload = 1

    init {
        mShowFilter = mShow!!
    }

    inner class ViewHolder(val binding: BuildItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        //return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false))
        val binding =
            BuildItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder)
        {
            with(mShowFilter!![position])
            {
                //(holder as ViewHolder)
                binding.ivDownload.setOnClickListener {
                    clickCallback.viewDetailItem(mShowFilter!![position], mDownload)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    interface CallbackDetail {
        fun viewDetailItem(pos: InvoicesData, mShow: Int)
    }

    override fun getItemCount(): Int {
        return if (mShowFilter == null) 0 else mShowFilter!!.size
    }

    /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMonth: AppCompatTextView = itemView.findViewById(R.id.child_name)
    }*/
}