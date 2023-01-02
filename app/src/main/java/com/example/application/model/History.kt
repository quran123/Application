package com.example.application.model

import android.os.Parcel
import android.os.Parcelable

data class History (
    var id: Int,
    var user_name:String?,
    var quiz_name: String?,
    var question: String?,
    var option1: String?,
    var option2: String?,
    var option3: String?,
    var option4: String?,
    var isCorrect :Int,
    var optionSelected :String?,
    var date: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(user_name)
        parcel.writeString(quiz_name)
        parcel.writeString(question)
        parcel.writeString(option1)
        parcel.writeString(option2)
        parcel.writeString(option3)
        parcel.writeString(option4)
        parcel.writeInt(isCorrect)
        parcel.writeString(optionSelected)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<History> {
        override fun createFromParcel(parcel: Parcel): History {
            return History(parcel)
        }

        override fun newArray(size: Int): Array<History?> {
            return arrayOfNulls(size)
        }
    }
}
