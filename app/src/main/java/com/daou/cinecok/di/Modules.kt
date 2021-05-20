package com.daou.cinecok.di

import com.daou.cinecok.data.localdb.AppDatabase
import com.daou.cinecok.data.repository.*
import com.daou.cinecok.data.restapi.NSearchAPI
import com.daou.cinecok.ui.main.home.HomeViewModel
import com.daou.cinecok.ui.main.home.screening.ScreeningViewModel
import com.daou.cinecok.ui.main.scrap.ScrapViewModel
import com.daou.cinecok.ui.main.search.SearchViewModel
import com.daou.cinecok.ui.main.search.dialog.GenreSelectViewModel
import com.daou.cinecok.ui.main.search.dialog.MovieDetailViewModel
import com.daou.cinecok.utils.AppConstants
import com.daou.cinecok.utils.AppConstants.HEADER_KEY_NAVER_ID
import com.daou.cinecok.utils.AppConstants.HEADER_KEY_NAVER_SECRET
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val resourceModule = module {
    factory {
        androidContext().resources
    }
}

val localDBModule = module {
    single {
        AppDatabase.getInstance(androidContext())
    }
}

val apiModule = module {
    single {
        var builder = OkHttpClient.Builder().apply {
            interceptors().add(
                Interceptor { chain ->
                    chain.request().newBuilder()
                        .addHeader(HEADER_KEY_NAVER_SECRET, AppConstants.NAVER_API_SECRET)
                        .addHeader(HEADER_KEY_NAVER_ID, AppConstants.NAVER_API_ID)
                        .build()
                        .let { request ->
                            chain.proceed(request)
                        }
                })
        }
        Retrofit.Builder().baseUrl(AppConstants.NAVER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .apply {
                client(builder.build())
            }.build()
    }

    factory {
        (get() as Retrofit).create(NSearchAPI::class.java)
    }
}

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(),get(),get()) }
    viewModel{
        SearchViewModel(get())
    }
    viewModel{
        MovieDetailViewModel(get())
    }
    viewModel{
        ScrapViewModel(get())
    }
    viewModel{
        HomeViewModel(get())
    }
    viewModel{
        ScreeningViewModel(get())
    }

    //리포지토리 모듈 아님 .. 분리고민
    viewModel{
        GenreSelectViewModel()
    }
}