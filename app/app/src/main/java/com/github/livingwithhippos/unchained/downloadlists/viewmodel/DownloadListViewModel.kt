package com.github.livingwithhippos.unchained.downloadlists.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.github.livingwithhippos.unchained.base.model.repositories.CredentialsRepository
import com.github.livingwithhippos.unchained.base.model.repositories.DownloadRepository
import com.github.livingwithhippos.unchained.base.model.repositories.TorrentsRepository
import com.github.livingwithhippos.unchained.downloadlists.model.DownloadItem
import com.github.livingwithhippos.unchained.lists.model.DownloadPagingSource
import com.github.livingwithhippos.unchained.lists.model.TorrentPagingSource
import com.github.livingwithhippos.unchained.newdownload.model.TorrentItem

class DownloadListViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val downloadRepository: DownloadRepository,
    private val torrentsRepository: TorrentsRepository,
    private val credentialsRepository: CredentialsRepository
) : ViewModel() {

    // note: this value (pageSize) is triplicated when the first call is made. Yes it does, no I don't know why.
    val downloadsLiveData: LiveData<PagingData<DownloadItem>> = Pager(PagingConfig(pageSize = 10)) {
        DownloadPagingSource(downloadRepository, credentialsRepository)
    }.liveData.cachedIn(viewModelScope)

    val torrentsLiveData: LiveData<PagingData<TorrentItem>> = Pager(PagingConfig(pageSize = 10)) {
        TorrentPagingSource(torrentsRepository, credentialsRepository)
    }.liveData.cachedIn(viewModelScope)

}