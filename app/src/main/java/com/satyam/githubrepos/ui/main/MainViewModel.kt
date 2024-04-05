package com.satyam.githubrepos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyam.githubrepos.GithubApplication
import com.satyam.githubrepos.data.Status
import com.satyam.githubrepos.data.database.model.Repo
import com.satyam.githubrepos.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var repository: SearchRepository = GithubApplication.provideSearchRepo()

    private val _repos = MutableLiveData<Status<List<Repo>>>()
    val repos: LiveData<Status<List<Repo>>> get() = _repos

    fun getRepos(searchQuery: String) {
        viewModelScope.launch {
            repository.getRepos(searchQuery).collect {
                _repos.value = it
            }
        }
    }

}