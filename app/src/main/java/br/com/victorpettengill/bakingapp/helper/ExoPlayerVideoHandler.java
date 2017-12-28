package br.com.victorpettengill.bakingapp.helper;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
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

    private ExoPlayerVideoHandler(){}

    public void prepareExoPlayerForUri(Context context, Uri uri,
                                       SimpleExoPlayerView exoPlayerView){
        if(context != null && uri != null && exoPlayerView != null){
            if(!uri.equals(playerUri) || player == null){
                // Create a new player if the player is null or
                // we want to play a new video
                playerUri = uri;

                String userAgent = Util.getUserAgent(context, "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(uri,
                        new DefaultHttpDataSourceFactory(userAgent),
                        new DefaultExtractorsFactory(), null, null);

                player.prepare(mediaSource);
            }
            player.clearVideoSurface();
            player.setVideoSurfaceView(
                    (SurfaceView)exoPlayerView.getVideoSurfaceView());
            player.seekTo(player.getCurrentPosition() + 1);
            exoPlayerView.setPlayer(player);
        }
    }

    public void releaseVideoPlayer(){
        if(player != null)
        {
            player.release();
        }
        player = null;
    }

    public void goToBackground(){
        if(player != null){
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    public void goToForeground(){
        if(player != null){
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }
}
