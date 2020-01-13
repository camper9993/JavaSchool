package com.handtruth.javaschool.connect

import com.handtruth.javaschool.baseURL
import com.handtruth.javaschool.data.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.InputStream

interface ApiService {
    @POST("auth")
    @FormUrlEncoded
    fun auth(
        @Field("firstName" ) firstName: String,
        @Field("secondName") secondName: String,
        @Field("age") age: String,
        @Field("login") login: String,
        @Field("password") password: String): Single<AuthResponse>

    @POST("registration")
    @FormUrlEncoded
    fun registration(@Field("firstName" ) firstName: String,
                     @Field("secondName") secondName: String,
                     @Field("age") age: String,
                     @Field("login") login: String,
                     @Field("password") password: String): Single<RegisterResponse>

    @GET("modules/info")
    fun getModules(): Observable<List<ModuleInfo>>

    @GET("modules/info/{id}")
    fun getModuleInfo(@Path("id") id: Int): Observable<ModuleInfo>

    @GET("modules/{id}")
    fun getModule(@Path("id") id: Int): Observable<List<Lesson>>

    @GET("tests/test/{id}")
    fun getTests(@Path("id") id: Int): Observable<List<Test>>

    @GET("tests/{id}")
    fun getLessonTests(@Path("id") id: Int): Observable<List<ModuleTests>>

    @GET("file/text/{id}")
    fun download(@Path("id") id: String): Observable<String>



    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseURL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
