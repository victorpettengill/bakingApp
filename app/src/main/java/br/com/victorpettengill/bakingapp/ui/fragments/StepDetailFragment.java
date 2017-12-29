package br.com.victorpettengill.bakingapp.ui.fragments;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Step;
import br.com.victorpettengill.bakingapp.helper.ExoPlayerVideoHandler;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener{

    @BindView(R.id.container_player) LinearLayout containerPLayer;
    @BindView(R.id.container) ConstraintLayout constraintLayout;
    @BindView(R.id.instructions) TextView instructions;
    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.scrollView) ScrollView scrollView;


    private LinearLayout.LayoutParams originalParams;

    private static final String TAG = StepDetailFragment.class.getSimpleName();
    public static final String STEP_ARG = "step";
    private final String URI_ARG = "uri";
    private Step recipeStep;

    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private static PlaybackStateCompat.Builder mStateBuilder;

    private Dialog mFullScreenDialog;
    private boolean mExoPlayerFullscreen;

    private boolean handlingRotation = false;

    private Uri currentUri;

    public StepDetailFragment() {

    }

    public static StepDetailFragment newInstance(Step recipeStep) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_ARG, recipeStep);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Fragment", "OnCreate");

        if (getArguments() != null) {
            recipeStep = getArguments().getParcelable(STEP_ARG);

            currentUri = Uri.parse(recipeStep.getVideoURL());
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        handlingRotation = false;

        if(savedInstanceState != null) {
            recipeStep = savedInstanceState.getParcelable(STEP_ARG);
            currentUri = Uri.parse(savedInstanceState.getString(URI_ARG));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, rootView);

        instructions.setText(recipeStep.getDescription());
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.ic_placeholder));


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        handlingRotation = true;

        ExoPlayerVideoHandler.getInstance().goToBackground();

        if(recipeStep != null) {
            outState.putParcelable(STEP_ARG, recipeStep);
        }

        if(currentUri != null) {
            outState.putString(URI_ARG, currentUri.toString());
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null) {

            recipeStep = savedInstanceState.getParcelable(STEP_ARG);
            currentUri = Uri.parse(savedInstanceState.getString(URI_ARG));

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (currentUri != null && mPlayerView != null) {

            ExoPlayerVideoHandler.getInstance()
                    .prepareExoPlayerForUri(getContext(),
                            currentUri, mPlayerView);
            ExoPlayerVideoHandler.getInstance().goToForeground();

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    public void onStop() {
        super.onStop();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(!handlingRotation) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }

    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {

        if(originalParams == null) {
            originalParams = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
        }

        containerPLayer.removeView(mPlayerView);
        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {

        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);

        containerPLayer.addView(mPlayerView, 0);

        mPlayerView.setLayoutParams(originalParams);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MediaSessionCallBack extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if(mFullScreenDialog  == null) {
                initFullscreenDialog();
            }

            openFullscreenDialog();

        } else {

            closeFullscreenDialog();

        }

    }

    public interface FragmentStepComm {

        void onLandscape();
        void onPortrait();

    }

}
