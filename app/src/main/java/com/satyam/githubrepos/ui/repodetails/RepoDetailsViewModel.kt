package com.satyam.githubrepos.ui.repodetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyam.githubrepos.GithubApplication
import com.satyam.githubrepos.data.Status
import com.satyam.githubrepos.data.database.model.Repo
import com.satyam.githubrepos.data.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor() : ViewModel() {

    var repository: DetailsRepository = GithubApplication.provideDetailsRepo()

    private val _repoDetails = MutableLiveData<Status<Repo>>()
    val repoDetails: LiveData<Status<Repo>> get() = _repoDetails

    fun getRepoDetails(repoId: Long, ownerLogin: String, repoName: String) {
        viewModelScope.launch {
            repository.getRepoById(repoId, ownerLogin, repoName).collect {
                _repoDetails.value = it
            }
        }
    }

}