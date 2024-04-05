package com.satyam.githubrepos.ui.repodetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.satyam.githubrepos.BaseActivity
import com.satyam.githubrepos.R
import com.satyam.githubrepos.data.Status
import com.satyam.githubrepos.data.database.model.Repo
import com.satyam.githubrepos.databinding.ActivityRepoDetailsBinding
import com.satyam.githubrepos.utils.Constants
import com.satyam.githubrepos.utils.hide
import com.satyam.githubrepos.utils.loadImage
import com.satyam.githubrepos.utils.show


class RepoDetailsActivity : BaseActivity<ActivityRepoDetailsBinding, RepoDetailsViewModel>() {

    private val viewModel: RepoDetailsViewModel by viewModels()

    private var repoId: Long = 0L
    private var ownerLogin: String = ""
    private var repoName: String = ""

    override fun setLayoutId(): Int {
        return R.layout.activity_repo_details
    }

    override fun setViewModel(): RepoDetailsViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        repoId = intent.getLongExtra(Constants.INTENT_REPO_ID, 0L)
        ownerLogin = intent.getStringExtra(Constants.INTENT_OWNER_LOGIN).orEmpty()
        repoName = intent.getStringExtra(Constants.INTENT_REPO_NAME).orEmpty()
        viewModel.getRepoDetails(repoId, ownerLogin, repoName)

        viewModel.repoDetails.observe(this) {
            when (it) {
                is Status.Loading -> {
                    viewDataBinding.loader.show()

                }

                is Status.Success -> {
                    viewDataBinding.loader.hide()
                    setRepoDetails(it.data)
                }

                is Status.Error -> {
                    //TODO(to handle)
                }
            }
        }
    }


    private fun setRepoDetails(repo: Repo) {
        viewDataBinding.author.text = repo.owner.login
        viewDataBinding.title.text = repo.name
        viewDataBinding.description.text = repo.description
        loadImage(viewDataBinding.image, repo.owner.avatarUrl)

        viewDataBinding.holderLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl))
            startActivity(browserIntent)
        }
    }
}