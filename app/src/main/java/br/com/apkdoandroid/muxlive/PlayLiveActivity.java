package br.com.apkdoandroid.muxlive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import br.com.apkdoandroid.muxlive.api.MuxApiService;
import br.com.apkdoandroid.muxlive.api.RetrofitClient;
import br.com.apkdoandroid.muxlive.entities.LiveStream;
import br.com.apkdoandroid.muxlive.entities.LiveStreamsResponse;
import br.com.apkdoandroid.muxlive.helper.Constantes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayLiveActivity extends AppCompatActivity {
    private VideoView videoView;
    Boolean liveAtiva = false;
    private Thread thread;
    String streamKey = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_live);

        videoView = findViewById(R.id.videoView);
        verficarLive();
        // Defina a URL do vídeo
        String videoUrl = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             streamKey = extras.getString("live");
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

    private void getLives(){
         liveAtiva = false;
        MuxApiService service = RetrofitClient.classService(MuxApiService.class);
        Call<LiveStreamsResponse> call = service.getLiveStreams();

        call.enqueue(new Callback<LiveStreamsResponse>() {
            @Override
            public void onResponse(Call<LiveStreamsResponse> call, Response<LiveStreamsResponse> response) {
                Log.d(Constantes.TAG,"Sucesso: "+response.isSuccessful());
                Log.d(Constantes.TAG,"Code: "+response.code());
                Log.d(Constantes.TAG,response.body().toString());
                if(response.isSuccessful()){

                    List<LiveStream> liveStreams = response.body().getData();
                    if(liveStreams != null){
                        for(LiveStream item : liveStreams){

                                if( streamKey != null && !streamKey.isEmpty()){
                                    if(item.getPlayback_ids().get(0).getId().equals(streamKey) && !item.getStatus().equals("active")){
                                        alert();
                                    }
                                }


                        }
                    }

                }else{

                }
            }

            @Override
            public void onFailure(Call<LiveStreamsResponse> call, Throwable t) {
                Log.d(Constantes.TAG,"Erro: "+t.getMessage());
            }
        });
    }

    private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayLiveActivity.this);
        builder.setTitle("Aviso");
        builder.setMessage("A live acabou.");
        builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Fechar a Activity
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void verficarLive() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // Coloque o código que deseja executar aqui
                        System.out.println("Código sendo executado... verificando se a live tá ativa");
                        getLives();
                        // Aguarda 5 segundos
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // Captura a interrupção da thread
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        thread.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (thread != null) {
            thread.interrupt();
        }
    }
}