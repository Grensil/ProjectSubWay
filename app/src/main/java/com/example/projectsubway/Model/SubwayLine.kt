package com.example.projectsubway.Model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


data class SubwayLine(
    @SerializedName("idx")val idx: Int,
    @SerializedName("name")val name: String,
    @SerializedName("sub_name")val sub_name: String,
    @SerializedName("color_code")val color_code: String



)