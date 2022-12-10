package org.sopt.sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import coil.load
import org.sopt.sample.databinding.FragmentMyPageBinding

class MyPageFragment: Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding이 생성되지 않았습니다." }
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imgSample.load(it)
        // 여기서 Uri가 오고 이걸 사용하면 됨
    }

    val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            5
        )
    ) { uris ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                binding.imgSample.load(uris[0])
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtImageBring.setOnClickListener{
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }
}