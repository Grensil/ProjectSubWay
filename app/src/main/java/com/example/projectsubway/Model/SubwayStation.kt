package com.example.projectsubway.Model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class SubwayStation(
    @SerializedName("idx")val idx: Int,
    @SerializedName("name")val name: String,
    @SerializedName("subway_lines")val subway_lines: List<Int>
)