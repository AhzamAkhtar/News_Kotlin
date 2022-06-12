package com.example.android.news_kotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class Note (@ColumnInfo(name = "text")val text:String){
    // It will Generate Id Automatically
    @PrimaryKey(autoGenerate = true)var id=0

}