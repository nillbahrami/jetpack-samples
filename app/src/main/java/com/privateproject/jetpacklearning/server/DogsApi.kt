package com.privateproject.jetpacklearning.server

import com.privateproject.jetpacklearning.model.DogBread
import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBread>>
}