package com.example.projectsubway.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projectsubway.Model.SearchDAO
import com.example.projectsubway.Model.SubWayEntity
import com.example.projectsubway.Model.SubwayStation

@androidx.room.Database(entities = [SubWayEntity::class], version = 2, exportSchema = false)
abstract class Database() : RoomDatabase() {

    abstract fun searchDao(): SearchDAO
    companion object{
        private var INSTANCE : Database? = null;



        fun getInstance(context: Context) : Database?
        {
            if(INSTANCE == null){
                synchronized(Database::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    Database::class.java,"subway-db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }



}