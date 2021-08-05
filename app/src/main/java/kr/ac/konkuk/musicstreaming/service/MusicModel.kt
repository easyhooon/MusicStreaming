package kr.ac.konkuk.musicstreaming.service

data class MusicModel(
    val id: Long,
    val track: String,
    val streamUrl: String,
    val artist: String,
    val coverUrl: String,
    val isPlaying: Boolean = false //view 에서 필요한 값
)
