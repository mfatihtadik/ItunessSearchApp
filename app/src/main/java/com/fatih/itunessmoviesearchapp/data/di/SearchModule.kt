package com.fatih.itunessmoviesearchapp.data.di

import android.content.Context
import com.fatih.itunessmoviesearchapp.SearchApplication
import com.fatih.itunessmoviesearchapp.data.api.ApiServices
import com.fatih.itunessmoviesearchapp.data.repository.SearchRepositoryImpl
import com.fatih.itunessmoviesearchapp.domain.repository.SearchRepository
import com.fatih.itunessmoviesearchapp.domain.usecase.GetSearchUseCase
import com.fatih.itunessmoviesearchapp.presentation.Util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchApi() : ApiServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(api : ApiServices) : SearchRepository{
        return SearchRepositoryImpl(apiServices = api)
    }

    @Provides
    @Singleton
    fun provideGetSearchUsecase(repository : SearchRepositoryImpl) :GetSearchUseCase{
        return GetSearchUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAppContext(app: SearchApplication) : Context = app.applicationContext

/*
    @Provides
    @Singleton
    fun provideFavFilmsDatabase(@ApplicationContext appContext: Context): FavFilmsDatabase {
        return Room.databaseBuilder(
            appContext,
            FavFilmsDatabase::class.java,
            "favfilms"
        ).build()
    }

    @Provides
    fun provideFavFilmsDao(database: FavFilmsDatabase): FavDao {
        return database.favDao()
    }
 */
}