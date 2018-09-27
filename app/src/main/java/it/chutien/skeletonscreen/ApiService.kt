package it.chutien.skeletonscreen

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by ChuTien on ${1/25/2017}.
 */
interface ApiService {

    @GET("menu.php")
    fun getData(): Observable<List<Recipe>>

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.androidhive.info/json/shimmer/")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}