package org.sopt.sample.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import org.sopt.sample.LoginActivity
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.home.adapter.RepoAdapter
import org.sopt.sample.remote.PersonServicePool
import org.sopt.sample.remote.RequestSignUpDTO
import org.sopt.sample.remote.ResponsePersonDTO
import org.sopt.sample.remote.ResponseSignUpDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private val personService = PersonServicePool.personService
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체 생성이 필요합니다." }

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
        // 서버통신 성공 -> 데이터 들고와서 fragment 내 RecyclerView에 뿌리기
        personService.getData().enqueue(object : Callback<ResponsePersonDTO> {
            override fun onResponse(
                call: Call<ResponsePersonDTO>,
                response: Response<ResponsePersonDTO>
            ) {
                // Log.e("log", response.body().toString())
                when (response.code()) {
                    400 -> {
                        Log.e("400", "error")
                    }
                    500 -> {
                        Log.e("500", "error")
                    }
                }
                if (response.isSuccessful) {
                    Log.e("success", "success")
                    // 서버에서 데이터 전송받음 <현재 성공>
                }
            }
            override fun onFailure(call: Call<ResponsePersonDTO>, t: Throwable) {
                Log.e("server fail", "${t.message.toString()}")
            }
        })
    }
     // 어댑터 설정 및 더미 데이터를 통한 뷰 생성, 서버랑 연결 실습하면 이쪽이 아마 불바다(?)가 될지도?

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