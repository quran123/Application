package com.example.application.model

import android.os.Parcel
import android.os.Parcelable

data class Questions(
    var id: Int,
    var quiz_name: String?,
    var question: String?,
    var correctAnswer: String?,
    var option1: String?,
    var option2: String?,
    var option3: String?,
    var option4: String?,
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
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(quiz_name)
        parcel.writeString(question)
        parcel.writeString(correctAnswer)
        parcel.writeString(option1)
        parcel.writeString(option2)
        parcel.writeString(option3)
        parcel.writeString(option4)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Questions> {
        override fun createFromParcel(parcel: Parcel): Questions {
            return Questions(parcel)
        }

        override fun newArray(size: Int): Array<Questions?> {
            return arrayOfNulls(size)
        }
    }
}
