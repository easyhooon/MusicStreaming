package kr.ac.konkuk.musicstreaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //MainActivity 가 onCreate 될때 newInstance 함수 가 호출->PlayerFragment 를 반환
        //fragmentContainer 에 replace 됨
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PlayerFragment.newInstance())
            .commit()
    }
}