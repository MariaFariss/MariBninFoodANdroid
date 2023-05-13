package com.example.maribninfood.model

import android.os.Parcel
import android.os.Parcelable
import com.example.maribninfood.dao.RecipeDao

class RecipeClass (var dataImage:Int, var dataTitle:String, var dataDesc: String, var dataDetailImage: Int):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dataImage)
        parcel.writeString(dataTitle)
        parcel.writeString(dataDesc)
        parcel.writeInt(dataDetailImage)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<RecipeClass> {
        override fun createFromParcel(parcel: Parcel): RecipeClass {
            return RecipeClass(parcel)
        }
        override fun newArray(size: Int): Array<RecipeClass?> {
            return arrayOfNulls(size)
        }
    }
}