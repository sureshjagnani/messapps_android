package com.messapps.adapter.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.messapps.databinding.EstimatesItemBinding
import com.messapps.model.progress.ProgressData


class ProgressAdapter
    (
    private var mShow: List<ProgressData>, private val clickCallback: CallbackDetail
) : RecyclerView.Adapter<ProgressAdapter.ViewHolder>() {

    private var mShowFilter: List<ProgressData> =
        mutableListOf()

    init {
        mShowFilter = mShow!!
    }
    inner class ViewHolder(val binding: EstimatesItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        //return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false))
        val binding = EstimatesItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        with(holder)
        {
            with( mShowFilter!![position])
            {
                //(holder as ViewHolder)
                binding.childName.text = this.projectName
                binding.tvHour.text = buildString {
                    append("Estimated Hour:")
                    append(this@with.hours)
                }
                binding.tvPrice.text = buildString {
                    append("Estimated Price:")
                    append(this@with.estimateAmout)
                }
                binding.llDetail.setOnClickListener {
                    clickCallback.viewDetailItem(mShowFilter!![position])
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    interface CallbackDetail {
        fun viewDetailItem(pos: ProgressData)
    }

    override fun getItemCount(): Int {
        return if (mShowFilter == null) 0 else mShowFilter!!.size
    }

    /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMonth: AppCompatTextView = itemView.findViewById(R.id.child_name)
    }*/
}