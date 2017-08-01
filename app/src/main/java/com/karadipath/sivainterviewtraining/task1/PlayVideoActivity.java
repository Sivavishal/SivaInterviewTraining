package com.karadipath.sivainterviewtraining.task1;

import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

import com.karadipath.sivainterviewtraining.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PlayVideoActivity extends AppCompatActivity {
    VideoView mVideoView;
    TextView mTxtSubTitle;
    Context mContext;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mContext = PlayVideoActivity.this;
        getSupportActionBar().setTitle("Play Video ");
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
        }
        mVideoView = (VideoView)findViewById(R.id.videoView);
        mTxtSubTitle  = (TextView) findViewById(R.id.txt_subTitleTxt);

        playvideowithsubtitle();
    }

    private int findTrackIndexFor(int mediaTrackType, MediaPlayer.TrackInfo[] trackInfo) {
        int index = -1;
        for (int i = 0; i < trackInfo.length; i++) {
            if (trackInfo[i].getTrackType() == mediaTrackType) {
                return i;
            }
        }
        return index;
    }

    private String getSubtitleFile(int resId) {
        String fileName = getResources().getResourceEntryName(resId);
        File subtitleFile = getFileStreamPath(fileName);
        if (subtitleFile.exists()) {
            return subtitleFile.getAbsolutePath();
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(resId);
            outputStream = new FileOutputStream(subtitleFile, false);
            copyFile(inputStream, outputStream);
            return subtitleFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStreams(inputStream, outputStream);
        }
        return "";
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }

    // A handy method I use to close all the streams
    private void closeStreams(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable stream : closeables) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
            playvideowithsubtitle();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
            playvideowithsubtitle();
        }
    }
    @Override
    public void onBackPressed() {
        if(mVideoView != null){
            if(mVideoView.isPlaying()){
                mVideoView.stopPlayback();
            }
        }
        if(player != null){
            if(player.isPlaying()){
                player.stop();
            }
        }
        getSupportActionBar().show();
       finish();
    }
    protected void onPause(){
        super.onPause();


        if(mVideoView != null){
            if(mVideoView.isPlaying()){
                mVideoView.stopPlayback();
            }
        }

        mVideoView.start();
        if(player != null){
            if(player.isPlaying()){
                player.stop();
            }
        }
    }
    void playvideowithsubtitle(){

        if(mVideoView != null){
            if(mVideoView.isPlaying()){
                mVideoView.stopPlayback();
            }
        }

        mVideoView.start();
if(player != null){
    if(player.isPlaying()){
        player.stop();
    }
}
        player = MediaPlayer.create(mContext, R.raw.itteaser);
        String uriPath = "android.resource://com.karadipath.sivainterviewtraining/"+R.raw.itteaser;
        Uri uri = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //  mVideoView.addSubtitleSource(getResources().openRawResource(R.raw.itteasersrt), MediaFormat.createSubtitleFormat("text/vtt", Locale.ENGLISH.getLanguage()));
        }
        mVideoView.requestFocus();
        mVideoView.canPause();
        mVideoView.showContextMenu();
        mVideoView.canSeekForward();

        try {
            player.addTimedTextSource(getSubtitleFile(R.raw.own),
                    MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            int textTrackIndex = findTrackIndexFor(
                    MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, player.getTrackInfo());
            if (textTrackIndex >= 0) {
                player.selectTrack(textTrackIndex);
            } else {
            }
            player.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
                @Override
                public void onTimedText(final MediaPlayer mediaPlayer, final TimedText timedText) {
                    if (timedText != null) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                int seconds = mediaPlayer.getCurrentPosition() / 1000;

                                mTxtSubTitle.setText(timedText.getText());
                            }
                        });
                    }
                }
            });
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
