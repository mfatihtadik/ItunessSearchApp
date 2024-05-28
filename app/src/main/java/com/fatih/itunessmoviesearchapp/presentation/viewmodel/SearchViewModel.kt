package com.fatih.itunessmoviesearchapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fatih.itunessmoviesearchapp.data.repository.SearchRepositoryImpl
import com.fatih.itunessmoviesearchapp.data.dto.Movie
import com.fatih.itunessmoviesearchapp.data.local.FavDao
import com.fatih.itunessmoviesearchapp.data.local.FavFilms
import com.fatih.itunessmoviesearchapp.data.local.FavFilmsDatabase
import com.fatih.itunessmoviesearchapp.domain.usecase.GetSearchUseCase
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: GetSearchUseCase, application: Application) : BaseViewModel(application) {

    private val _favFilms = MutableLiveData<List<FavFilms>>()
    val favFilms: LiveData<List<FavFilms>> = _favFilms


    fun loadMovies(searchTerm : String) : Flow<PagingData<Movie>>{
        return useCase.loadMovies(searchTerm).cachedIn(viewModelScope)
    }
    suspend fun storeInSql(filmAdi : String, imgUrl : String, desc: String){
          val dao = FavFilmsDatabase(getApplication()).favDao()
            val favFilms =FavFilms(filmAdi,imgUrl,desc)
            dao.insertFilm(favFilms)
    }
    fun fetchAllFavFilms() {
        viewModelScope.launch {
            val films = FavFilmsDatabase(getApplication()).favDao().getAll()
            _favFilms.postValue(films)
        }
    }
    fun deleteFilm(favFilm: FavFilms) {
        viewModelScope.launch {
            FavFilmsDatabase(getApplication()).favDao().deleteFilm(favFilm)
            fetchAllFavFilms()
        }
    }
}