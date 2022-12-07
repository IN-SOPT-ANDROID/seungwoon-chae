package org.sopt.sample.home

import ContentUriRequestBody
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.sopt.sample.databinding.FragmentMyPageBinding
import org.sopt.sample.viewmodel.LoginViewModel
import org.sopt.sample.viewmodel.MusicAddViewModel
import org.sopt.sample.viewmodel.MusicViewModel

class MyPageFragment : Fragment() {
    private val viewModel by viewModels<MusicAddViewModel>()
    private var _binding: FragmentMyPageBinding? = null
    private val map: HashMap<String, RequestBody> = hashMapOf()
    private val binding get() = requireNotNull(_binding) { "binding이 생성되지 않았습니다." }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtImageBring.setOnClickListener {
            imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        binding.btnMusic.setOnClickListener {
            val name = binding.etTitleNew.text.toString()
//            Log.e("tag", name)
//            var requestName: RequestBody =
//                name.toRequestBody("text/plain".toMediaTypeOrNull())
            val singer = binding.etSingerNew.text.toString()
//            Log.e("tag", singer)
//            val requestSinger: RequestBody =
//                singer.toRequestBody("text/plain".toMediaTypeOrNull())
//
//            map["singer"] = requestSinger
//            map["title"] = requestName
            map["request"] = "{\"title\": \"$name\", \"singer\": \"$singer\"}".toRequestBody("application/json".toMediaTypeOrNull())
            // "request"라는 이름을 가진 객체를 맵에 넣고 title와 singer 통째로 application/json 변환, 서버는 지금 text/plain이 아니라 json을 원한다.
            // 앞으로도 서버와 교류할 때 자료형 및 응답 타입 철저하게 확인하고 진행할 것.
            viewModel.postMusic(map!!)
            Toast.makeText(context, "음악 추가 성공", Toast.LENGTH_SHORT).show()
        }
    }

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            Log.e("hello", it.toString())
            binding.imgSample.load(it)
            viewModel.setRequestBody(ContentUriRequestBody(requireContext(), it))
            Log.e("uris ->: ", viewModel.image.toString())
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


}