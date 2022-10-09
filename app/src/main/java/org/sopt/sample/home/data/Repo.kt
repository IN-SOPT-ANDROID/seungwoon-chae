package org.sopt.sample.home.data

import androidx.annotation.DrawableRes
import java.lang.reflect.Constructor

data class Repo(
    @DrawableRes val image: Int?,
    val name: String,
    val author: String?,
    val type: Int = 1
) {
    constructor(name: String, type: Int) : this(null, name, null, type)
}
