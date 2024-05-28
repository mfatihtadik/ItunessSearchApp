package com.fatih.itunessmoviesearchapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavFilms(
    @ColumnInfo(name = "fName")
    var fName : String,
    @ColumnInfo(name = "fImg")
    var fImg : String,
    @ColumnInfo(name = "fDesc")
    var fDesc : String
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int =0
}
