package com.fatih.itunessmoviesearchapp.domain.usecase

import androidx.paging.PagingData
import com.fatih.itunessmoviesearchapp.data.dto.Movie
import com.fatih.itunessmoviesearchapp.data.repository.SearchRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(private val repository: SearchRepositoryImpl) {

    fun loadMovies(searchTerm : String) : Flow<PagingData<Movie>> {
        return repository.getMovie(searchTerm)
    }
}