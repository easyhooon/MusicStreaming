package kr.ac.konkuk.musicstreaming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kr.ac.konkuk.musicstreaming.databinding.FragmentPlayerBinding
import kr.ac.konkuk.musicstreaming.service.MusicDto
import kr.ac.konkuk.musicstreaming.service.MusicService
import kr.ac.konkuk.musicstreaming.service.mapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var binding: FragmentPlayerBinding? = null
    private var isWatchingPlayListView = true
    private var player: SimpleExoPlayer? = null
    private lateinit var playListAdapter: PlayListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding

        initPlayView(fragmentPlayerBinding)
        initPlayListButton(fragmentPlayerBinding)
        initPlayControlButtons(fragmentPlayerBinding)
        initRecyclerView(fragmentPlayerBinding)

        getVideoListFromServer()
    }

    private fun initPlayControlButtons(fragmentPlayerBinding: FragmentPlayerBinding) {

        fragmentPlayerBinding.playControlImageView.setOnClickListener {
            val player = this.player ?: return@setOnClickListener

            if(player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }
        fragmentPlayerBinding.skipNextImageView.setOnClickListener {

        }
        fragmentPlayerBinding.skipPrevImageView.setOnClickListener {

        }
    }

    private fun initPlayView(fragmentPlayerBinding: FragmentPlayerBinding) {
        context?.let {
            player = SimpleExoPlayer.Builder(it).build()
        }

        fragmentPlayerBinding.playerView.player = player

        binding?.let { binding ->
            //player 에 listener 를 달아줌
            player?.addListener(object: Player.EventListener{

                //재생이 될때, 일시정지가 될때 콜백으로 내려옴
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)

                    if(isPlaying) {
                        binding.playControlImageView.setImageResource(R.drawable.ic_baseline_pause_48)
                    } else {
                        binding.playControlImageView.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    }
                }
            })
        }
    }

    private fun initRecyclerView(fragmentPlayerBinding: FragmentPlayerBinding) {
        playListAdapter = PlayListAdapter {
            //todo 음악을 재생
        }

        fragmentPlayerBinding.playListRecyclerView.apply {
            adapter = playListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initPlayListButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playlistImageView.setOnClickListener {
            //todo 만약 서버에서 데이터가 다 불려오지 않은 상태 일 때

            fragmentPlayerBinding.playerViewGroup.isVisible = isWatchingPlayListView
            fragmentPlayerBinding.playListViewGroup.isVisible =isWatchingPlayListView.not()

            isWatchingPlayListView = !isWatchingPlayListView
        }
    }

    private fun getVideoListFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusicService::class.java)
            .also {
                it.listMusics()
                    .enqueue(object: Callback<MusicDto> {
                        override fun onResponse(
                            call: Call<MusicDto>,
                            response: Response<MusicDto>
                        ) {
                            Log.d("PlayerFragment", "${response.body()}")

                            response.body()?.let {
                                //nullable 을 풀어줌
                                //순서를 넣어줌
                                val modelList = it.musics.mapIndexed{ index, musicEntity ->
                                    musicEntity.mapper(index.toLong())
                                }

                                playListAdapter.submitList(modelList)
                            }
                        }

                        override fun onFailure(call: Call<MusicDto>, t: Throwable) {

                        }
                    })
            }
    }


    companion object {
        fun newInstance(): PlayerFragment {
            return PlayerFragment()
        }
    }
}