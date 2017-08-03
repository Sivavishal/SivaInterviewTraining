package com.karadipath.sivainterviewtraining.task1;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.karadipath.sivainterviewtraining.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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
       /* if (subtitleFile.exists()) {
            return subtitleFile.getAbsolutePath();
        }*/

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
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)) {
            if(player != null){
                if(player.isPlaying()){
                    player.stop();
                }
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
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)) {
            if(player != null){
                if(player.isPlaying()){
                    player.stop();
                }
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
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)) {
            if(player != null){
                if(player.isPlaying()){
                    player.stop();
                }
            }
        }

        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTxtSubTitle.setVisibility(View.INVISIBLE);
           // int toSpeak = getResources().getIdentifier("raw/itteaser", null, getPackageName());
           // player = MediaPlayer.create(mContext,toSpeak);
        }else{
            player = MediaPlayer.create(mContext, R.raw.itteaser);
        }
        String uriPath = "android.resource://com.karadipath.sivainterviewtraining/"+ R.raw.itteaser;

       uri = Uri.parse(uriPath);

       
        mVideoView.setVideoURI(uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            InputStream i = getApplicationContext().getResources().openRawResource(R.raw.ownvtt);
            mVideoView.addSubtitleSource(i ,MediaFormat.createSubtitleFormat("text/vtt",Locale.ENGLISH.getLanguage()));

         //  mVideoView.addSubtitleSource(getResources().openRawResource(R.raw.ownvtt), MediaFormat.createSubtitleFormat("text/vtt", Locale.ENGLISH.getLanguage()));
        }else{

            try {
                 /*    try{
                int rresid =  getResources().getIdentifier("raw/own", null, getPackageName());
               String filepath =  getSubtitleFile(rresid);
                Uri newuri =  Uri.parse(filepath);
                player.addTimedTextSource(mContext,newuri,
                        MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            }catch (Exception ex){
                ex.printStackTrace();
            }*/



            /*try{

                player.addTimedTextSource(mContext,Uri.parse(uriSrtPath),
                        MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            }catch (Exception ex){
                ex.printStackTrace();
            }*/
            int rresid =  getResources().getIdentifier("raw/own", null, getPackageName());
            player.addTimedTextSource(getSubtitleFile(rresid),
                    MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            int textTrackIndex = findTrackIndexFor(
                    MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, player.getTrackInfo());
            if (textTrackIndex >= 0) {
                player.selectTrack(textTrackIndex);
                player.setVolume(0,0);
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


        try {







            mVideoView.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File setImageUriFile() {

        File file = null;

        File dir = new File("file:///android_asset");
        if (dir.exists()) {

        }
        try{
            file = new File("file:///android_asset/itteaser");
        }
       catch (Exception ex){
           ex.printStackTrace();
       }
        /*String uriPath = "android.resource://com.karadipath.sivainterviewtraining/"+R.raw.itteaser;
        File directory = new File("android.resource://com.example.photoalbum/raw/");*/

        return file;
    }
}
