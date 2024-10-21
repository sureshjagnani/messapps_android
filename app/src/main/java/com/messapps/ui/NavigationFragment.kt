package com.messapps.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.messapps.adapter.ChildAdapter
import com.messapps.adapter.NaviAdapter
import com.messapps.databinding.FragmentNavigationBinding
import com.messapps.data.model.NavigationData
import com.messapps.utils.getData
import com.messapps.utils.getTitle


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NavigationFragment : Fragment(), NaviAdapter.ClickCallback, ChildAdapter.CallbackChild {
    private var _binding: FragmentNavigationBinding? = null
    private var view: View? = null
    private var navigationAdapter: NaviAdapter? = null
    var expandableListTitle: MutableList<NavigationData>? = null
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
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        mContext = requireContext()
        view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expandableListDetail = getData()
        expandableListTitle = getTitle()// ArrayList(expandableListDetail!!.keys)
        navigationAdapter =
            NaviAdapter(requireContext(), expandableListTitle!!, expandableListDetail!!, this, this)
        binding.rvNavigation.setAdapter(navigationAdapter!!)
        //binding.rvNavigation.setHasFixedSize(true)
        //val layoutManager =
        //    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //binding.rvNavigation.layoutManager = layoutManager


//        binding.rvNavigation.setOnGroupExpandListener(OnGroupExpandListener { groupPosition ->
////            Toast.makeText(
////                getApplicationContext(),
////                expandableListTitle.get(groupPosition) + " List Expanded.",
////                Toast.LENGTH_SHORT
////            ).show()
//        })
//
//        binding.rvNavigation.setOnGroupCollapseListener(OnGroupCollapseListener { groupPosition ->
////            Toast.makeText(
////                getApplicationContext(),
////                expandableListTitle.get(groupPosition) + " List Collapsed.",
////                Toast.LENGTH_SHORT
////            ).show()
//        })
//
//        binding.rvNavigation.setOnChildClickListener(OnChildClickListener { parent, v, groupPosition, childPosition, id ->
////            Toast.makeText(
////                getApplicationContext(),
////                expandableListTitle.get(groupPosition)
////                        + " -> "
////                        + expandableListDetail!![expandableListTitle.get(groupPosition)]!![childPosition],
////                Toast.LENGTH_SHORT
////            ).show()
//            false
//        })
    }

    private fun setAdapter() {

    }


    //    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_navigation, container, false)
//    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NavigationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NavigationFragment().apply {
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

//    override fun viewDetail(pos: Int?) {
//    }

    override fun viewDetailChild(order: NavigationData) {
    }

    override fun callNaviFragment(pos: Int?, order: NavigationData) {
    }
}