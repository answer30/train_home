package com.pin.train_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    Context con;

    String TAG = MainActivity.class.toString();

    private PlayerView exoPlayerView;
    private SimpleExoPlayer player;

    ImageView imageView , imagebtn;




    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private Long playbackPosition = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = this;

        exoPlayerView = findViewById(R.id.exoPlayerView);

        imageView  = findViewById(R.id.imageView);
        imagebtn  = findViewById(R.id.imagebtn);
    }


    public void btnClick(View view) {
        switch (view.getId()) {
            case  R.id.btn_setting:{
//                Intent intent = new Intent(con, MoviActivity.class);
//                startActivity(intent);



                Intent intent = new Intent();

                intent.setClassName( "com.android.settings",  "com.android.settings.Settings");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            break;
            case R.id.btn_01:{
//                Intent intent = new Intent(con, VODPlayerActivity.class);
//                startActivity(intent);
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.korail.motrex.webapp");
                startActivity(intent);
            }
            break;
            case R.id.btn_02:{
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.pin.train_pin_vod");
                startActivity(intent);
            }
            break;
            case R.id.btn_03:{
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);


//                String url = "rtmp://175.158.11.174/psfm/_definst_/sfm.stream?id=2101&si=5&secure=YWU2MGJmMzZhZmNiNDUzNTA2N2MyZGUwZTM2Zjc2ZmVjZjIzOTViYmEzNDkyMzg4NDNlNmNmMGQ3NzcwZWVkNjM0ZTFjYTBhNDc3MzQ2Y2ViZDIxZGE0MTdlMmNhZTdiZTMyMjJkM2RhNTM3OGExNTMxZDZiZjg5ZmY1M2I5OWFmMGZlN2U3OTljOGY4OTMzMGIyZmYyNjc4MmIyNmE0ZGZjNzI4ODZjYWM5NDJmZjk3MmJiMGU1N2FjOGEyYjI5ZmFjZTY1OGExNDUyN2VjMmE1NDY0YzQxNTgxYTk0NDBmNjYzZDEyYjNjY2Q2ZmJmZTBiMTAzMjcwODU3NTljMjJiNjgxYjA4NTZkYjJlMzRkYzU1NGEzZTRjNjMzNGUy&csu=false";


//                String url = "http://serpent0.duckdns.org:8088/kbs1radio.pls";
////                String url = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4";
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//                try {
//                    mediaPlayer.setDataSource(url);
//                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
//
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            mediaPlayer.start();
//                        }
//                    });
//
//
//                }catch (Exception e){
//                    Log.d(TAG, "btnClick: " + e);
//                }


            }
            break;
            case R.id.btn_04:{
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.korail.motrex.shopapp");
                startActivity(intent);
            }
            break;


            case R.id.imageView:{


                if( player.isPlaying()) {
                    player.pause();

                    imagebtn.setImageResource(R.drawable.paly_nor);
                }else{
                    player.play();
                    imagebtn.setImageResource(R.drawable.pause_nor);
                }


                Log.d(TAG, "btnClick: ");

            }
            break;



        }

    }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
        imagebtn.setImageResource(R.drawable.paly_nor);
    }


    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer() {
        if (player == null) {

            player =  new SimpleExoPlayer.Builder(con).build();

            //플레이어 연결
            exoPlayerView.setPlayer(player);

        }

//        String sample = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

//        String sample = "https://1radio.gscdn.kbs.co.kr/1radio_192_1.m3u8?Expires=1630461232&Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly8xcmFkaW8uZ3NjZG4ua2JzLmNvLmtyLzFyYWRpb18xOTJfMS5tM3U4IiwiQ29uZGl0aW9uIjp7IkRhdGVMZXNzVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNjMwNDYxMjMyfX19XX0_&Signature=UdOEbM2-pNjo44KkbcOC4LyOIUw2ROSMLhitw~Ex1opajcthj4bSFnoqjDuU9arIKGdk2arcidaBV-TkWGtSB6riIDx9z36rUHQlpX3oE9svv11S6PbcHAJXZWFIH3qQF1N~jk52OGsjdyWzKNdwQ4iyFPc2PW-NCm3RHN5rmJsgOCtL0I1J-mRKF6kph-R1iu6eGZKAnlXwoC3DaeEnYkI6gPNqmv0Jn7nnjFo5azz-44Bj9mQP7blYyEWjOtJibjngre9FbEVF-CMXRdlsLKGHO1Dake5hejDKYJ5HU4CuIyTZIMX2jxOBduMJrIZLNdcyDDIKtu75SWcrX29kuQ__&Key-Pair-Id=APKAICDSGT3Y7IXGJ3TA";

//        String sample = "https://radiolive.sbs.co.kr/love/lovefm.stream/playlist.m3u8?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzAzMzM1NjQsInBhdGgiOiIvbG92ZWZtLnN0cmVhbSIsImR1cmF0aW9uIjotMSwidW5vIjoiMGMwM2JlNmUtMmNkZC00YjI0LTg1YTYtODkyZGRiZjcxZDM2IiwiaWF0IjoxNjMwMjkwMzY0fQ.GZBO3BRPpWS8L1VRBIWPB6Htdgc7yNSwpTCz3bikQRs";


//        String sample = "https://radiolive.sbs.co.kr/power/powerfm.stream/playlist.m3u8?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzAzNTAzMjQsInBhdGgiOiIvcG93ZXJmbS5zdHJlYW0iLCJkdXJhdGlvbiI6LTEsInVubyI6IjRlNWMwZTVlLTk1ODUtNDgxYS1hZDc3LWZjZjlkY2IwMzFiNSIsImlhdCI6MTYzMDMwNzEyNH0.rEb3d1I-j6r8pRXJ430ivZRu1zJJWE0d5V8DewYmscs";
//        String sample = "rtsp://192.168.0.101:8554/edit";
        String sample = "https://radiolive.sbs.co.kr/power/powerfm.stream/playlist.m3u8?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzA1MDE2OTksInBhdGgiOiIvcG93ZXJmbS5zdHJlYW0iLCJkdXJhdGlvbiI6LTEsInVubyI6IjAwODg5N2RlLTNhY2ItNGQ0Ny04NDNhLWJhNTVkYTJhMDgxYiIsImlhdCI6MTYzMDQ1ODQ5OX0.aXTshkte7GuPRP-gn3VNFiv4UiqLcUSpUCZgyUo7r2o";
//        String sample = "http://serpent0.duckdns.org:8088/sbs2fm.pls";

//        MediaSource mediaSource = buildMediaSource(Uri.parse("http://tbs.hscdn.com/tbsradio/fm/playlist.m3u8"));
        MediaSource mediaSource = buildMediaSource(Uri.parse(sample));


//        player.setMediaItem(MediaItem.fromUri(Uri.parse(sample)));

//        https://1radio.gscdn.kbs.co.kr/1radio_192_1.m3u8?Expires=1630461232&Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly8xcmFkaW8uZ3NjZG4ua2JzLmNvLmtyLzFyYWRpb18xOTJfMS5tM3U4IiwiQ29uZGl0aW9uIjp7IkRhdGVMZXNzVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNjMwNDYxMjMyfX19XX0_&Signature=UdOEbM2-pNjo44KkbcOC4LyOIUw2ROSMLhitw~Ex1opajcthj4bSFnoqjDuU9arIKGdk2arcidaBV-TkWGtSB6riIDx9z36rUHQlpX3oE9svv11S6PbcHAJXZWFIH3qQF1N~jk52OGsjdyWzKNdwQ4iyFPc2PW-NCm3RHN5rmJsgOCtL0I1J-mRKF6kph-R1iu6eGZKAnlXwoC3DaeEnYkI6gPNqmv0Jn7nnjFo5azz-44Bj9mQP7blYyEWjOtJibjngre9FbEVF-CMXRdlsLKGHO1Dake5hejDKYJ5HU4CuIyTZIMX2jxOBduMJrIZLNdcyDDIKtu75SWcrX29kuQ__&Key-Pair-Id=APKAICDSGT3Y7IXGJ3TA
      //  https://1radio.gscdn.kbs.co.kr/1radio_192_1.m3u8?Expires=1630462805&Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly8xcmFkaW8uZ3NjZG4ua2JzLmNvLmtyLzFyYWRpb18xOTJfMS5tM3U4IiwiQ29uZGl0aW9uIjp7IkRhdGVMZXNzVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNjMwNDYyODA1fX19XX0_&Signature=Lq0MNKm626KhhoUOSDStY4zkolxx-2vtVOR6mo7o~FwAb3pIxRoQ6egVSZsflgOlpGnG4Yt~wc80ADRcFMFN7Tz7Q4D~vaxcGe83niNz-QcEtMzJvM0PAQtCuADvBskG9vBd46Oq-0wbD4FYgJjYuIdReRL-kgfGSdexKy3pWXevlHyydqR-bnhd735iSZtgbocL4mcrqeJn9WVDtriUD7~3dgH-aj7YgTdT2ChqeghUYjSOm2PXHrlprL5Z0eQJULuoyeA0bOx3HkaLc8l2C5GCYTkM2wwF3kkwgka11sS-sOUYz2909vrk5fEiBjohRi9ho42b-YWNqZnIEyDcGQ__&Key-Pair-Id=APKAICDSGT3Y7IXGJ3TA

        player.setMediaSource(mediaSource);
        player.prepare();
//        player.play();

        //prepare
//        player.prepare(mediaSource, true, false);

        //start,stop
//        player.setPlayWhenReady(playWhenReady);


    }



    private MediaSource buildMediaSource(Uri uri) {

//        String userAgent = Util.getUserAgent(this, "blackJin");
//        val url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"

        MediaItem mediaItem = MediaItem.fromUri(uri);

//        return new ProgressiveMediaSource.Factory(new DefaultHttpDataSource.Factory()).createMediaSource(mediaItem);

        return new HlsMediaSource.Factory(new DefaultHttpDataSource.Factory()).createMediaSource(mediaItem);


//        return new RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(uri));


    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();

            exoPlayerView.setPlayer(null);
            player.release();
            player = null;

        }
    }
}
