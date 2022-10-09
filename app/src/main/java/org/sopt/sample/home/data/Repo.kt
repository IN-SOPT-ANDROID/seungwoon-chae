package org.sopt.sample.home.data

import androidx.annotation.DrawableRes
import org.sopt.sample.R

data class Repo(
    @DrawableRes val image: Int = R.drawable.git,
    val name: String = "제목",
    val author: String = "작성자",
    val type: Int = 1 // 기본값 1(깃레포를 의미)
)