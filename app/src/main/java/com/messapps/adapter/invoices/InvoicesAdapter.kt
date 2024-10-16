package com.messapps.adapter.invoices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.messapps.databinding.EstimatesItemBinding
import com.messapps.databinding.InvoicesItemBinding
import com.messapps.model.invoices.InvoicesData
import com.messapps.model.progress.ProgressData


class InvoicesAdapter
    (
    private var mShow: List<InvoicesData>, private val clickCallback: CallbackDetail
) : RecyclerView.Adapter<InvoicesAdapter.ViewHolder>() {

    private var mShowFilter: List<InvoicesData> =
        mutableListOf()
    private val mView = 0
    private val mDownload = 1

    init {
        mShowFilter = mShow!!
    }

    inner class ViewHolder(val binding: InvoicesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        //return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false))
        val binding =
            InvoicesItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder)
        {
            with(mShowFilter!![position])
            {
                //(holder as ViewHolder)
                binding.childName.text = this.invoiceDate
                binding.ivView.setOnClickListener {
                    clickCallback.viewDetailItem(mShowFilter!![position], mView)
                }
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