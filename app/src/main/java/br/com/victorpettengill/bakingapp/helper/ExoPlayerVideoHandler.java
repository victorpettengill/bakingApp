package br.com.victorpettengill.bakingapp.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Helper class obtained from: https://medium.com/tall-programmer/fullscreen-functionality-with-android-exoplayer-5fddad45509f
 */

public class ExoPlayerVideoHandler
{
    private static ExoPlayerVideoHandler instance;

    public static ExoPlayerVideoHandler getInstance(){
        if(instance == null){
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }

    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;
    private long position;
    private MediaSource mediaSource;

    private ExoPlayerVideoHandler(){}

    public void prepareExoPlayerForUri(Context context, Uri uri,
                                       SimpleExoPlayerView exoPlayerView){

        if(context != null && uri != null && exoPlayerView != null){

            if(!uri.equals(playerUri)) {
                String userAgent = Util.getUserAgent(context, "BakingApp");
                mediaSource = new ExtractorMediaSource(uri,
                        new DefaultHttpDataSourceFactory(userAgent),
                        new DefaultExtractorsFactory(), null, null);

            }

            if(player == null){

                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);


                player.seekTo(position);
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);

                playerUri = uri;
            }

            player.clearVideoSurface();
            player.setVideoSurfaceView(
                    (SurfaceView)exoPlayerView.getVideoSurfaceView());
            exoPlayerView.setPlayer(player);
        }
    }

    public void releaseVideoPlayer(){
        if(player != null)
        {
            position = 0;
            player.release();
        }
        player = null;
    }

    public void onPause() {
        if(player != null)
        {
            isPlayerPlaying = player.getPlayWhenReady();
            position = player.getCurrentPosition();
            player.release();
        }
        player = null;
    }

    public void goToBackground(){
        if(player != null){
            isPlayerPlaying = player.getPlayWhenReady();
            position = player.getCurrentPosition();
            player.setPlayWhenReady(true);
        }
    }

    public void goToForeground(){
        if(player != null){
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }
}
