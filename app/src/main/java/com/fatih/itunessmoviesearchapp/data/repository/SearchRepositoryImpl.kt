package com.fatih.itunessmoviesearchapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fatih.itunessmoviesearchapp.data.api.ApiServices
import com.fatih.itunessmoviesearchapp.data.dto.Movie
import com.fatih.itunessmoviesearchapp.domain.paging.ItunesPagingSource
import com.fatih.itunessmoviesearchapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val apiServices: ApiServices) : SearchRepository {
    override fun getMovie(searchTerm: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = true),
            pagingSourceFactory = { ItunesPagingSource(apiServices,searchTerm) }
        ).flow
    }
}