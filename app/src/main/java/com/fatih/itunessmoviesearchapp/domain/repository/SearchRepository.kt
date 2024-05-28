package com.fatih.itunessmoviesearchapp.domain.repository

import androidx.paging.PagingData
import com.fatih.itunessmoviesearchapp.data.dto.Movie
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getMovie(searchTerm : String) : Flow<PagingData<Movie>>
}