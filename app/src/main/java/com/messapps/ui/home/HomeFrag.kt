package com.messapps.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.messapps.databinding.HomeMainBinding
import com.messapps.utils.ARG_PARAM1
import com.messapps.utils.ARG_PARAM2

class HomeFrag : Fragment() {
    private var _binding: HomeMainBinding? = null
    private var view: View? = null
    //private var estimateAdapter: EstimateViewAdapter? = null
    //var eListTitle: MutableList<EstimateData>? = null
    //var expandableListDetail: HashMap<String, List<NavigationData>>? = null

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
        _binding = HomeMainBinding.inflate(inflater, container, false)
        mContext = requireContext()
        view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //eListTitle = getEstimate()
        //setAdapter()
    }

//    private fun setAdapter() {
//        binding.rvEstimates.setHasFixedSize(true)
//        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvEstimates.layoutManager = layoutManager
//        estimateAdapter = EstimateViewAdapter(eListTitle!!, this)
//        binding.rvEstimates.setAdapter(estimateAdapter!!)
//    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFrag().apply {
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
}