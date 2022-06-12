package com.example.android.news_kotlin

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // conflict is for not letting duplicates item
    // suspend let the fun work in background thread
    //because insert and delete fun I/O , that are very heavy to work in Main Thread
    // That stop the UI or Main thread
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

}