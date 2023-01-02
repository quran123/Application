package com.example.application.data

import android.os.Parcel
import android.os.Parcelable

fun QuizTables(
    id: Int,
    quiz_name: String?,
    //  date: String?
) {
    var id:Int?=id
    var quiz_name: String? = quiz_name
    // var date: String? = date

}

data class QuizTables(
    var id: Int,
    var quiz_name: String?,
    var date: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(quiz_name)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuizTables> {
        override fun createFromParcel(parcel: Parcel): QuizTables {
            return QuizTables(parcel)
        }

        override fun newArray(size: Int): Array<QuizTables?> {
            return arrayOfNulls(size)
        }
    }
}