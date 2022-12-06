package org.sopt.sample.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.sample.databinding.LayoutGithubRepoBinding
import org.sopt.sample.remote.Person
import org.sopt.sample.remote.ResponseMusicDTO

class MusicAdapter(Item: List<ResponseMusicDTO.Data>, context: Context) : RecyclerView.Adapter<MusicAdapter.RepoViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var repoList: List<ResponseMusicDTO.Data> = emptyList()
    lateinit var gitBinding: LayoutGithubRepoBinding

    class RepoViewHolder(
        private val binding: LayoutGithubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseMusicDTO.Data) {
            Glide.with(binding.root)
                .load(data.image)
                .circleCrop()
                .into(binding.imgGithubRepo)
            binding.txtGithubRepoName.text = data.title
            binding.txtGithubRepoAuthor.text = data.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        gitBinding = LayoutGithubRepoBinding.inflate(inflater, parent, false)
        return RepoViewHolder(gitBinding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(repoList[position])
    }

    override fun getItemCount() = repoList.size

    fun setRepoList(repolist: List<ResponseMusicDTO.Data>) {
        this.repoList = repolist.toList() // 원본이 바뀌어도 문제가 생기지 않도록 "얇은 복사" 처리
        // notifyDataSetChanged() // 새로운 데이터셋을 인식시켜 이를 기반으로 다시 리사이클러 뷰를 그리도록 함
        notifyItemRangeChanged(0, repoList.size)
    }
}