package com.example.projectsubway.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.projectsubway.Adapter
import com.example.projectsubway.Model.*
import com.example.projectsubway.R
import com.example.projectsubway.Service.SubwayService
import com.example.projectsubway.database.Database
import com.example.projectsubway.databinding.FragmentSearchBinding
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Observer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding: FragmentSearchBinding? = null

    lateinit var navController: NavController

    lateinit var  myadapter : Adapter

    private lateinit var searchDAO: SearchDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        val database = Database.getInstance(requireContext())
        searchDAO = database!!.searchDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://teacher.tictoccroc-devtest.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SubwayService::class.java)
            .let {
                it.SubwayLines()
                    .enqueue(object: Callback<Subway> {
                        override fun onResponse(
                            call: Call<Subway>,
                            response: Response<Subway>
                        ) {

                            val body = response.body() as Subway;
                            val station = body.subway_stations as  ArrayList<SubwayStation>
                            val lines = body.subway_lines as ArrayList<SubwayLine>;

                            val recyclerView = binding!!.recyclerviewSearch;
                            val layoutManager = LinearLayoutManager(requireContext());
                            recyclerView.layoutManager = layoutManager;

                            myadapter = Adapter(requireContext(), station, lines)
                            myadapter.setStationClickListener(StationClickListener());
                            recyclerView.adapter = myadapter;

                        }
                        override fun onFailure(call: Call<Subway>, t: Throwable) {
                            Log.d("failed..",t.toString())
                        }
                    })
            }
    }



    inner class StationClickListener : Adapter.onStationClickListener{
        override fun onStationClick(sub_idx: Int,stationName: String, subway_lines:String) {

            //room 저장 작업
            //val insert = Runnable {
            val entity = SubWayEntity(sub_idx,stationName,subway_lines)
            searchDAO.insert(entity)
            //}
            //Thread(insert).start()

            navController.popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding!!.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding!!.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                Log.i("1",p0.toString())
                //여기 p0.toStirng()의 내용이  포함되어있는 값만 recyclerview에 뿌려줘야함
                myadapter?.filter?.filter(p0.toString())


            }
        })
    }
}