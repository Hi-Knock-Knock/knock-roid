package com.all_the_best.knock_knock.parent.faq.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParentFaqData(
        val index: Int,
        val title: String,
        val content: Int,
        var isScrapped: Boolean
): Parcelable