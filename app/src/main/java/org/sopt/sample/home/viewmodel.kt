package org.sopt.sample.home

import androidx.lifecycle.ViewModel
import org.sopt.sample.home.data.Repo

class HomeViewModel: ViewModel()  {
    val mockRepoList = listOf<Repo>(
        Repo(
            name = "타이틀",
            type = 0
        ),
        Repo(
            name = "여행",
            author = "볼빨간사춘기"
        ),
        Repo(
            name = "검정색 하트",
            author = "릴러말즈",
        ),
        Repo(
            name = "북향",
            author = "다이나믹 듀오",
        ),
        Repo(
            name = "불면증",
            author = "다이나믹 듀오",
        ),
        Repo(
            name = "노땡큐",
            author = "에픽하이",
        ),
        Repo(
            name = "trip",
            author = "릴러말즈",
        ),
        Repo(
            name = "끝사랑",
            author = "김범수",
        ),
        Repo(
            name = "All I wanna do",
            author = "박재범",
        ),
        Repo(
            name = "니가 없는 밤",
            author = "비오",
        ),
    )
}