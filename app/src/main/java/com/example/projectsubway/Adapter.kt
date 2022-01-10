package com.example.projectsubway

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsubway.Model.SubwayLine
import com.example.projectsubway.Model.SubwayStation
import com.example.projectsubway.databinding.ItemStationBinding



class Adapter(private val context:Context, private var stations:ArrayList<SubwayStation>, private val lines:ArrayList<SubwayLine>) : RecyclerView.Adapter<Adapter.ViewHolder>(), Filterable {
    private var stationList: ArrayList<SubwayStation>? = stations


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return stationList!!.size
    }

    override fun getFilter(): Filter?{
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                stationList = if(charString.isEmpty()) {
                    stations
                }
                else {
                    val filterdList = ArrayList<SubwayStation>()
                    if(stations != null) {
                        for(name in stations) {
                            if ( p0.toString()in name.name) {
                                filterdList.add(name)
                            }
                        }
                    }
                    filterdList
                }
                val filterResults = FilterResults()
                filterResults.values = stationList
                return filterResults

            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                val station = p1?.values as ArrayList<SubwayStation>
                stationList = station
                notifyDataSetChanged()
            }
        }
    }



    inner class ViewHolder(private val binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root) {

        val stationname : TextView = binding.stationname
        var textViewList = ArrayList<TextView>();

        private var a = false;

        fun bind(item: SubwayStation, lines: ArrayList<SubwayLine>, context:Context) {
            stationname.text = item.name
            //stationlines.setImageResource()
            var cnt : Int = 0
            lines.forEach{

                when(cnt) // 비효율적
                {

                    0 -> {
                        if (it.name.get(0).isDigit())
                        {
                            binding.textView1.setText(it.name.get(0) + "  ")
                        }
                        else {
                            binding.textView1.setText(it.name+ "  ")
                        }
                        binding.textView2.setText("")
                        binding.textView3.setText("")
                        binding.textView4.setText("")
                        binding.textView1.setTextColor(Color.parseColor(it.color_code))
                    }
                    1 -> {
                        if (it.name.get(0).isDigit())
                        {
                            binding.textView2.setText(it.name.get(0) + "  ")
                        }
                        else {
                            binding.textView2.setText(it.name+ "  ")
                        }
                        binding.textView3.setText("")
                        binding.textView4.setText("")
                        binding.textView2.setTextColor(Color.parseColor(it.color_code))
                        }
                    2 -> {
                        if (it.name.get(0).isDigit())
                        {
                            binding.textView3.setText(it.name.get(0) + "  ")
                        }
                        else {
                            binding.textView3.setText(it.name+ "  ")
                        }
                        binding.textView4.setText("")
                        binding.textView3.setTextColor(Color.parseColor(it.color_code))
                        }
                    3 -> {
                        if (it.name.get(0).isDigit())
                        {
                            binding.textView4.setText(it.name.get(0) + "  ")
                        }
                        else {
                            binding.textView4.setText(it.name+ "  ")
                        }
                        binding.textView4.setTextColor(Color.parseColor(it.color_code))
                        }

                }
                cnt+=1

            }
            a = true;

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val viewHolder= holder as ViewHolder;
        viewHolder.itemView.setOnClickListener {
            stationClickListener.onStationClick( stations.get(position).idx,stations.get(position).name, stations.get(position).subway_lines.toString());

        }

        val station = stations.get(position);
        val lineList = ArrayList<SubwayLine>();

        station.subway_lines.forEach{
            val lineID = it // lineID 는 실제 데이터의 호선 정보들
            lines.forEach {
                if(lineID == it.idx){ //   it.idx 는 subwaylines의 역 정보
                    lineList.add(it);
                }
            }
        }


        holder.bind(stations.get(position),lineList, context)
    }


    interface onStationClickListener{
        fun onStationClick(sub_idx : Int, stationName:String, subway_lines:String);
    }

    private lateinit var stationClickListener : onStationClickListener;

    fun setStationClickListener(stationClickListener:onStationClickListener){
        this.stationClickListener = stationClickListener
    }


}