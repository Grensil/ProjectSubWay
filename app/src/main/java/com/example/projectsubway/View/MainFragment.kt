package com.example.projectsubway.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.projectsubway.Adapter
import com.example.projectsubway.Model.SearchDAO
import com.example.projectsubway.Model.SubWayEntity
import com.example.projectsubway.Model.Subway
import com.example.projectsubway.Model.SubwayLine
import com.example.projectsubway.R
import com.example.projectsubway.SearchedAdapter
import com.example.projectsubway.Service.SubwayService
import com.example.projectsubway.database.Database
import com.example.projectsubway.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding: FragmentMainBinding? = null

    lateinit var navController: NavController

    lateinit var  myadapter : SearchedAdapter

    private lateinit var searchDAO: SearchDAO



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val database = Database.getInstance(requireContext())
        searchDAO = database!!.searchDao()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        return binding!!.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding!!.recyclerviewSearchedlist;
        val layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.layoutManager = layoutManager;

        myadapter = SearchedAdapter(requireContext(),searchDAO.getAll() as ArrayList<SubWayEntity>)
        myadapter.setSearchedStationClickListener(SearchedStationClickListener());
        recyclerView.adapter = myadapter;

        navController = Navigation.findNavController(view)
        binding!!.textView.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_searchFragment)

        }

        binding!!.buttonDeleteAll.setOnClickListener {
            searchDAO.deleteAll()
            binding!!.recyclerviewSearchedlist.visibility = View.INVISIBLE
        }

    }

    inner class SearchedStationClickListener : SearchedAdapter.onSearchedStationClickListener{
        override fun onSearchedStationClick(sub_idx: Int,stationName: String, subway_lines:String) {



            //room 저장 작업
            val insert = Runnable {
            val entity = SubWayEntity(sub_idx,stationName,subway_lines)
            searchDAO.delete(entity)
            }
            Thread(insert).start()

            //실시간 -> ?
            myadapter.notifyDataSetChanged()
            Log.i("success",searchDAO.getAll().toString())



        }
    }

}