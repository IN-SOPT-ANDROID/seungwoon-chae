package org.sopt.sample.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.home.adapter.PersonAdapter
import org.sopt.sample.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private val homeViewmodel by viewModels<HomeViewModel>()
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
        binding.progressBar.isVisible = true // 로딩뷰 활성화
        homeViewmodel.getData()
        homeViewmodel.successGet.observe(viewLifecycleOwner){ success ->
            if(success){
                Log.d(homeViewmodel.getResult.value?.data.toString(), "data")
                val adapter = context?.let { it1 ->
                    homeViewmodel.getResult.value?.let {
                        PersonAdapter(homeViewmodel.getResult.value!!.data, it1).apply {
                            Log.d(homeViewmodel.getResult.value!!.toString(), "data")
                            setRepoList(homeViewmodel.getResult.value!!.data)
                        }
                    }
                }
                binding.rvRepos.adapter = adapter
            }
            binding.progressBar.isVisible = false // 서버통신 완료 -> 로딩뷰 비활성화
        }
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