package org.sopt.sample.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.home.adapter.RepoAdapter
import org.sopt.sample.home.data.Repo

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체 생성하고 써라 진짜 콱싀" }

    private val mockRepoList = listOf<Repo>(
        Repo(
            name = "타이틀",
            type = 0
        ),
        Repo(
            image = R.drawable.git,
            name = "여행",
            author = "볼빨간사춘기"
        ),
        Repo(
            image = R.drawable.git,
            name = "검정색 하트",
            author = "릴러말즈",
        ),
        Repo(
            image = R.drawable.git,
            name = "북향",
            author = "다이나믹 듀오",
        ),
        Repo(
            image = R.drawable.git,
            name = "불면증",
            author = "다이나믹 듀오",

            ),
        Repo(
            image = R.drawable.git,
            name = "노땡큐",
            author = "에픽하이",

            ),
        Repo(
            image = R.drawable.git,
            name = "trip",
            author = "릴러말즈",

            ),
        Repo(
            image = R.drawable.git,
            name = "끝사랑",
            author = "김범수",
        ),

        Repo(
            image = R.drawable.git,
            name = "All I wanna do",
            author = "박재범",
        ),
        Repo(
            image = R.drawable.git,
            name = "니가 없는 밤",
            author = "비오",
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RepoAdapter(requireContext())
        binding.rvRepos.adapter = adapter
        adapter.setRepoList(mockRepoList)
    } // 어댑터 설정 및 더미 데이터를 통한 뷰 생성, 서버랑 연결 실습하면 이쪽이 아마 불바다(?)가 될지도?

    fun viewToFirst() {
        binding.rvRepos.scrollToPosition(0)
    } // 리사이클러 뷰의 최상단으로 이동

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}