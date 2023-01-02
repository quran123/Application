package com.example.application.model

import android.os.Parcel
import android.os.Parcelable

data class HistoryList (
    var id: Int,
    var user_name:String?,
    var quiz_name: String?,
    var total_question: Int?,
    var correct_question: Int?,
    var score :Int?,
    var date: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(user_name)
        parcel.writeString(quiz_name)
        total_question?.let { parcel.writeInt(it) }
        correct_question?.let { parcel.writeInt(it) }
        score?.let { parcel.writeInt(it) }
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistoryList> {
        override fun createFromParcel(parcel: Parcel): HistoryList {
            return HistoryList(parcel)
        }

        override fun newArray(size: Int): Array<HistoryList?> {
            return arrayOfNulls(size)
        }
    }
}
