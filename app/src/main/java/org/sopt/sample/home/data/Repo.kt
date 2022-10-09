package org.sopt.sample.home.data

import androidx.annotation.DrawableRes
import java.lang.reflect.Constructor

data class Repo(
    @DrawableRes val image: Int?,
    val name: String,
    val author: String?,
    val type: Int = 1 // 기본값 1(깃레포를 의미)
) {
    constructor(name: String, type: Int) : this(null, name, null, type) {}
    // 타이틀(등의 다른 뷰홀더)을 위한 생성자, type 변수를 통해 viewtype을 확인하도록 유도
}
