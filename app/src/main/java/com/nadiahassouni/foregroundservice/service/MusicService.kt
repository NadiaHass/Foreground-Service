package com.nadiahassouni.foregroundservice.service

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.IBinder
import com.nadiahassouni.foregroundservice.R
import com.nadiahassouni.foregroundservice.ui.MainActivity

class MusicService : Service() {

    private lateinit var musicPlayer: MediaPlayer
    override fun onBind(p0: Intent?): IBinder? {
      return null
    }

    override fun onCreate() {
        super.onCreate()
        initializeMediaPlayer()
        createNotification()
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        showNotification()

        if(musicPlayer.isPlaying){
            musicPlayer.stop()
        }else{
            musicPlayer.start()
        }

        return START_STICKY
    }

    private fun showNotification() {
        val notificationIntent = Intent(this , MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this , 0 , notificationIntent , 0)
        val notification = Notification.Builder(this , "1")
            .setContentText("Music Player")
            .setSmallIcon(R.drawable.aurora)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1 , notification)
    }

    private fun initializeMediaPlayer() {
        musicPlayer = MediaPlayer.create(this , R.raw.aurora_runaway)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(100F , 100F)
    }

    private fun createNotification() {
        if (SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("1" , "My channel" ,
            NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(channel)
        }
    }
}