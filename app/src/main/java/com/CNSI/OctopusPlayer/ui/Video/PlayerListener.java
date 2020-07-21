package com.CNSI.OctopusPlayer.ui.Video;

import android.util.Log;
import org.videolan.libvlc.MediaPlayer;
import java.lang.ref.WeakReference;
public class PlayerListener implements MediaPlayer.EventListener {

    private static String TAG = "PlayerListener";
    private WeakReference<VideoActivity> mOwner;


    public PlayerListener(VideoActivity owner) {
        mOwner = new WeakReference<VideoActivity>(owner);
    }

    @Override
    public void onEvent(MediaPlayer.Event event) {
        VideoActivity player = mOwner.get();

        switch(event.type) {
            case MediaPlayer.Event.EndReached:
                Log.d(TAG, "MediaPlayerEndReached");
                player.releasePlayer();
                break;
            case MediaPlayer.Event.Playing:

            case MediaPlayer.Event.Paused:
                Log.d(TAG, "Pause");
            case MediaPlayer.Event.Stopped:
                Log.d(TAG, "Strop");
            default:
                break;
        }
    }
}
