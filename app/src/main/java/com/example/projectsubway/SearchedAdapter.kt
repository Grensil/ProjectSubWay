package com.example.projectsubway

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsubway.Model.SubWayEntity
import com.example.projectsubway.Model.SubwayStation
import com.example.projectsubway.databinding.ItemSearchedBinding

import com.example.projectsubway.databinding.ItemStationBinding


class SearchedAdapter(private val context:Context, private var stations:ArrayList<SubWayEntity>) : RecyclerView.Adapter<SearchedAdapter.ViewHolder>() {

    private var stationList: ArrayList<SubWayEntity>? = stations


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return stationList!!.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.itemView.setOnClickListener {
            //요소 삭제
            searchedstationClickListener.onSearchedStationClick(stations.get(position).idx,stations.get(position).name,stations.get(position).subway_lines)

        }
        val data = stationList!!.get(position).name
        val data2 = stationList!!.get(position).subway_lines
        holder.bind(data,data2)
    }
    inner class ViewHolder(private val binding: ItemSearchedBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subwayname: String, subwaylines : String) {
            binding.stationname.text = subwayname
            binding.textView1.text = subwaylines
        }
    }
    interface  onSearchedStationClickListener {
        fun onSearchedStationClick(sub_idx : Int, stationName:String, subway_lines:String)
    }
    private lateinit var searchedstationClickListener : onSearchedStationClickListener

    fun setSearchedStationClickListener(searchedStationClickListener: onSearchedStationClickListener) {
        this.searchedstationClickListener = searchedStationClickListener
    }




}