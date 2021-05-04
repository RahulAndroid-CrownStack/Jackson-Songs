package com.coderbychance.jecksonsong.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat


@Parcelize
data class SongInfo(
    val wrapperType:String,
    val artistId:Long,
    val collectionId:Long,
    val artistName:String,
    val collectionName:String,
    val collectionCensoredName:String,
    val artistViewUrl:String,
    val collectionViewUrl:String,
    val artworkUrl60:String,
    val artworkUrl100:String,
    val collectionPrice:Double,
    val collectionExplicitness:String,
    val trackCount:Int,
    val copyright:String,
    val country:String,
    val currency:String,
    val releaseDate:String,
    val primaryGenreName:String,
    val previewUrl:String,
    val description:String
):Parcelable
