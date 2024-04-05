package com.satyam.githubrepos.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.satyam.githubrepos.BaseActivity
import com.satyam.githubrepos.R
import com.satyam.githubrepos.data.Status
import com.satyam.githubrepos.data.database.model.Repo
import com.satyam.githubrepos.databinding.ActivityMainBinding
import com.satyam.githubrepos.ui.repodetails.RepoDetailsActivity
import com.satyam.githubrepos.utils.Constants
import com.satyam.githubrepos.utils.OnViewClickListener
import com.satyam.githubrepos.utils.hide
import com.satyam.githubrepos.utils.hideKeyboard
import com.satyam.githubrepos.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var reposAdapter: ReposAdapter

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setViewModel(): MainViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        viewDataBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.getRepos(query.trim())
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        viewDataBinding.list.adapter = reposAdapter
        reposAdapter.setItemClick(object : OnViewClickListener {
            override fun <T> onItemClick(view: View, data: T) {
                super.onItemClick(view, data)
                (data as? Repo)?.let {
                    startActivity(Intent(this@MainActivity, RepoDetailsActivity::class.java).apply {
                        putExtra(Constants.INTENT_REPO_ID, it.id)
                        putExtra(Constants.INTENT_OWNER_LOGIN, it.owner.login)
                        putExtra(Constants.INTENT_REPO_NAME, it.name)
                    })
                }
            }
        })

        viewModel.repos.observe(this) {
            when (it) {
                is Status.Loading -> {
                    viewDataBinding.loader.show()

                }

                is Status.Success -> {
                    viewDataBinding.loader.hide()
                    reposAdapter.setData(it.data)
                }

                is Status.Error -> {
                    //TODO(to handle)
                }
            }
        }
    }
}