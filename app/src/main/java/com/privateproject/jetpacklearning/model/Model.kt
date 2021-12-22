package com.privateproject.jetpacklearning.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DogBread(
    @SerializedName("id")
    @ColumnInfo(name = "bread_id")
    val breadId: String?,

    @SerializedName("name")
    @ColumnInfo(name = "dog_name")
    val dogBread: String?,

    @SerializedName("life_span")
    @ColumnInfo(name = "life_span")
    val lifeSpan: String?,

    @SerializedName("bread_group")
    @ColumnInfo(name = "bread_group")
    val breadGroup: String?,

    @SerializedName("bred_for")
    @ColumnInfo(name = "bread_for")
    val bredFor: String?,

    @SerializedName("temperament")
    @ColumnInfo(name = "temperament")
    val temp: String?,

    @SerializedName("url")
    @ColumnInfo(name = "dog_url")
    val imageUrl: String?
){
    // body class
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class DogPalette(var color: Int) {

}
