package org.sopt.sample.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.home.adapter.PersonAdapter
import org.sopt.sample.remote.PersonServicePool
import org.sopt.sample.remote.ResponsePersonDTO
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
        // 서버통신 성공 -> 데이터 들고와서 fragment 내 RecyclerView에 뿌리기
        personService.getData().enqueue(object : Callback<ResponsePersonDTO> {
            override fun onResponse(
                call: Call<ResponsePersonDTO>,
                response: Response<ResponsePersonDTO>
            ) {
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
                    val adapter = response.body()?.let {
                        context?.let { it1 ->
                            PersonAdapter(it.data, it1).apply{
                                setRepoList(it.data)
                            }
                        }
                    }
                    binding.rvRepos.adapter = adapter
                }
            }
            override fun onFailure(call: Call<ResponsePersonDTO>, t: Throwable) {
                Log.e("server fail", "${t.message.toString()}")
            }
        })
    }

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