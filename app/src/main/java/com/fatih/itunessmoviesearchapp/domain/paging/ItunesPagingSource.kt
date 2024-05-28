package com.fatih.itunessmoviesearchapp.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fatih.itunessmoviesearchapp.data.api.ApiServices
import com.fatih.itunessmoviesearchapp.data.dto.Movie
import retrofit2.HttpException
import java.io.IOException

class ItunesPagingSource(
    private val apiServices: ApiServices,
    private val searchTerm : String
) : PagingSource<Int,Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?:0

        return try {
            val response = apiServices.getViews(term = searchTerm, offset = position, limit = 12)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == 0) null else position -12,
                nextKey = if (movies.isEmpty()) null else position +12
            )
        } catch (e : IOException){
            LoadResult.Error(e)
        } catch (e : HttpException){
            LoadResult.Error(e)
        }
    }
}