package com.messapps.ui.estimates

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.messapps.MainActivity
import com.messapps.adapter.estimates.EstimateViewAdapter
import com.messapps.databinding.EstimatesMainBinding
import com.messapps.model.NavigationData
import com.messapps.model.estimates.EstimateData
import com.messapps.utils.getEstimate

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EstimatesFrag : Fragment(), EstimateViewAdapter.CallbackDetail {
    private var _binding: EstimatesMainBinding? = null
    private var view: View? = null
    private var estimateAdapter: EstimateViewAdapter? = null
    var eListTitle: MutableList<EstimateData>? = null
    var expandableListDetail: HashMap<String, List<NavigationData>>? = null

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
        _binding = EstimatesMainBinding.inflate(inflater, container, false)
        mContext = requireContext()
        view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eListTitle = getEstimate()
        setAdapter()
    }

    private fun setAdapter() {
        binding.rvEstimates.setHasFixedSize(true)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvEstimates.layoutManager = layoutManager
        estimateAdapter = EstimateViewAdapter(eListTitle!!, this)
        binding.rvEstimates.setAdapter(estimateAdapter!!)
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
            EstimatesFrag().apply {
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

    override fun viewDetailItem(pos: EstimateData) {
        (activity as MainActivity?)?.updateChildFragment()
    }
}