package com.example.projectsubway.Model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SearchDAO {

    @Query("SELECT * FROM subway_table")
    fun getAll(): MutableList<SubWayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity : SubWayEntity)


    @Delete
    fun delete(entity : SubWayEntity)

    @Query("DELETE FROM subway_table")
    fun deleteAll()
}