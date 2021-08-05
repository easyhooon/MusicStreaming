package kr.ac.konkuk.musicstreaming.service

import com.google.gson.annotations.SerializedName

data class MusicEntity (
    //실제로 서버에서 내려오는 json key 값
    @SerializedName("track") val track: String,
    @SerializedName("streamUrl") val streamUrl: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("coverUrl") val coverUrl: String,
)