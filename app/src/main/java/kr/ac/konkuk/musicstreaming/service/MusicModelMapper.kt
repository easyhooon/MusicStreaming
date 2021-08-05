package kr.ac.konkuk.musicstreaming.service

//Music Entity 를 Music Model 로 바꾸는 작업

//확장함수
fun MusicEntity.mapper(id: Long): MusicModel =
    MusicModel(
        id = id,
        streamUrl = streamUrl, // == this.streamUrl
        coverUrl = coverUrl,
        track = track,
        artist = artist
    )