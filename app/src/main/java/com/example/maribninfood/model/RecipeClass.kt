package com.example.maribninfood.model

import android.os.Parcel
import android.os.Parcelable
class RecipeClass (var id: String,var dataImage:String, var dataTitle:String, var dataDesc: String, var newRecipes : Boolean, var category : String ):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!
//        parcel.readInt()
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dataImage)
        parcel.writeString(dataTitle)
        parcel.writeString(dataDesc)
        parcel.writeByte(if (newRecipes) 1 else 0)  // Write a byte representing the Boolean value
        parcel.writeString(category)
//        parcel.writeInt(dataDetailImage)
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