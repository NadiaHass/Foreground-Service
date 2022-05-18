package com.nadiahassouni.foregroundservice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nadiahassouni.foregroundservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivPlay.setOnClickListener {
            if(serviceIsPlaying(MusicService::class.java)){
                stopMusicService()
            }else{
                startMusicService()
            }
        }
    }

    private fun stopMusicService() {
        stopService(Intent(this , MusicService::class.java))
    }

    private fun startMusicService() {
        startService(Intent(this , MusicService::class.java))

    }

    private fun serviceIsPlaying(musicClass : Class<MusicService>): Boolean {
        val manager : ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for (service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)){
            if(musicClass.name.equals(service.service.className)){
                return true
            }
        }
        return false
    }
}