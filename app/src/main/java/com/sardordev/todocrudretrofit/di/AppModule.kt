package com.sardordev.todocrudretrofit.di

import com.google.gson.Gson
import com.sardordev.todocrudretrofit.data.api.TodoApi
import com.sardordev.todocrudretrofit.domain.AppRepository
import com.sardordev.todocrudretrofit.domain.AppRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun getTodoApi(): TodoApi {
        return Retrofit.Builder()
            .baseUrl("https://hvax.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }



    @Singleton
    @Provides
    fun getAppRepository(todoApi: TodoApi) : AppRepository {
        return AppRepositoryImp(todoApi)
    }


}