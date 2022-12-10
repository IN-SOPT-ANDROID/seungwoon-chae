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
import org.sopt.sample.databinding.FragmentSearchBinding
import org.sopt.sample.home.adapter.MusicAdapter
import org.sopt.sample.home.adapter.PersonAdapter
import org.sopt.sample.viewmodel.HomeViewModel
import org.sopt.sample.viewmodel.MusicViewModel

class SearchFragment : Fragment() { // 시간이 촉박하여 fragment 이름 수정 등 생략하고 바로 과제 수행
    private val musicViewModel by viewModels<MusicViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "바인딩 객체 생성이 필요합니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicViewModel.getData()
        musicViewModel.successGet.observe(viewLifecycleOwner){ it ->
            if(it){
                val adapter = context?.let { it1 ->
                    musicViewModel.getResult.value?.let {
                        MusicAdapter(musicViewModel.getResult.value!!.data, it1).apply {
                            setRepoList(musicViewModel.getResult.value!!.data)
                        }
                    }
                }
                binding.rvMusic.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}