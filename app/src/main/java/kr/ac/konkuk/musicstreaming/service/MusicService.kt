package kr.ac.konkuk.musicstreaming.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {

    @GET("/v3/7eee8276-6aec-40c7-a989-1913e66bb377")
    fun listMusics() : Call<MusicDto>
}