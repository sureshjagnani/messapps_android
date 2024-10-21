package com.messapps.ui.builds

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.messapps.adapter.builds.BuildsAdapter
import com.messapps.databinding.BuildMainBinding

import com.messapps.data.model.invoices.InvoicesData
import com.messapps.utils.ARG_PARAM1
import com.messapps.utils.ARG_PARAM2
import com.messapps.utils.getInvoices


class BuildsFrag : Fragment(), BuildsAdapter.CallbackDetail {
    private var _binding: BuildMainBinding? = null
    private var view: View? = null
    private var invoiceAdapter: BuildsAdapter? = null
    var iListTitle: MutableList<InvoicesData>? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val binding get() = _binding!!
    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BuildMainBinding.inflate(inflater, container, false)
        mContext = requireContext()
        view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iListTitle = getInvoices()
        setAdapter()
    }

    private fun setAdapter() {
        binding.rvBuilds.setHasFixedSize(true)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvBuilds.layoutManager = layoutManager
        invoiceAdapter = BuildsAdapter(iListTitle!!, this)
        binding.rvBuilds.setAdapter(invoiceAdapter!!)
    }


    //    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_navigation, container, false)
//    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BuildsFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun viewDetailItem(pos: InvoicesData, mShow: Int) {
        if (mShow == 0) {
            //Invoice View
        } else if (mShow == 1) {
            //Invoice Download
        }
    }
}