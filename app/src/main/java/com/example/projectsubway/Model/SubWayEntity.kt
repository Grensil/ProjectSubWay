package com.example.projectsubway.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "subway_table")
data class SubWayEntity(
    @PrimaryKey(autoGenerate = true)
    val idx: Int,
    val name: String,
    val subway_lines: String
)