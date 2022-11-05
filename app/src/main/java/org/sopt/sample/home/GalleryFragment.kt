package org.sopt.sample.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentGalleryBinding
import org.sopt.sample.home.adapter.GalleryAdapter

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding이 생성되지 않았습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpGallery.adapter = GalleryAdapter().apply {
            setItems(
                listOf(
                    R.drawable.github,
                    R.drawable.github
                )
            )
        }
    }
}