package br.com.apkdoandroid.muxlive;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayLiveActivity extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_live);

        videoView = findViewById(R.id.videoView);
        // Defina a URL do vídeo
        String videoUrl = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String streamKey = extras.getString("live");
            videoUrl = "https://stream.mux.com/"+streamKey+".m3u8";
        }else{
            Toast.makeText(this, "Nenhum dado recebido", Toast.LENGTH_SHORT).show();
            finish();
        }


        Uri videoUri = Uri.parse(videoUrl);

        // Crie um MediaController para controlar a reprodução do vídeo
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Defina a URL do vídeo para o VideoView e comece a reprodução
        videoView.setVideoURI(videoUri);
        videoView.start();
    }
}