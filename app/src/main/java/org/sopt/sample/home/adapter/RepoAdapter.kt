package org.sopt.sample.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.LayoutGithubRepoBinding
import org.sopt.sample.databinding.LayoutTextRepoBinding
import org.sopt.sample.home.data.Repo
import org.w3c.dom.Text

class RepoAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TEXT_FORMAT = 0 // 현재 뷰 객체가 타이틀임을 감지하기 위한 변수
    private val GIT_FORMAT = 1 // 현재 뷰 객체가 레포임을 감지하기 위한 변수
    private val inflater by lazy { LayoutInflater.from(context) }
    private var repoList: List<Repo> = emptyList()
    lateinit var textBinding: LayoutTextRepoBinding
    lateinit var gitBinding: LayoutGithubRepoBinding

    inner class RepoViewHolder( // 레포 뷰홀더
        private val binding: LayoutGithubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Repo) {
            binding.imgGithubRepo.setImageDrawable(binding.root.context.getDrawable(data.image!!))
            binding.txtGithubRepoName.text = data.name
            binding.txtGithubRepoAuthor.text = data.author
        }
    }

    inner class TextViewHolder( // 텍스트(타이틀) 뷰홀더
        private val binding: LayoutTextRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Repo) {
            binding.txtName.text = data.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TEXT_FORMAT) { // 뷰홀더 종류에 따라 바인딩을 구분해서 적용
            textBinding = LayoutTextRepoBinding.inflate(inflater, parent, false)
            TextViewHolder(textBinding)
        } else {
            gitBinding = LayoutGithubRepoBinding.inflate(inflater, parent, false)
            RepoViewHolder(gitBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (repoList[position].type == 0) // 뷰홀더 종류 감지
            TEXT_FORMAT
        else GIT_FORMAT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TextViewHolder) { // 뷰홀더 종류에 따라 바인딩 타입 결정
            (holder).onBind(repoList[position])
        } else {
            (holder as RepoViewHolder).onBind(repoList[position])
        }
    }

    override fun getItemCount() = repoList.size

    fun setRepoList(repoList: List<Repo>) {
        this.repoList = repoList.toList() // 원본이 바뀌어도 문제가 생기지 않도록 "얇은 복사" 처리
        notifyDataSetChanged() // 새로운 데이터셋을 인식시켜 이를 기반으로 다시 리사이클러 뷰를 그리도록 함
    }
}