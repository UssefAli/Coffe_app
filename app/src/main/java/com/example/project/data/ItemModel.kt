package com.example.project.data

import android.accessibilityservice.GestureDescription
import java.io.Serializable

data class ItemModel(
    var Id: Int = 0,
    var categoryId: String = "",
    var title:String = "" ,
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var extra: String = ""
) : Serializable
