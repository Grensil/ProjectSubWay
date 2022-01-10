package com.example.projectsubway.Service

import com.example.projectsubway.Model.Subway
import com.example.projectsubway.Model.SubwayLine
import com.example.projectsubway.Model.SubwayStation
import retrofit2.Call
import retrofit2.http.GET

interface SubwayService {

    @GET("/v1/filter/subway/version/1")
    fun SubwayLines() : Call<Subway>

    @GET("/v1/filter/subway/version/1")
    fun SubwayStations() : Call<SubwayStation>

}