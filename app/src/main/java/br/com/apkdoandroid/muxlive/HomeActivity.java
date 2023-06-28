package br.com.apkdoandroid.muxlive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import br.com.apkdoandroid.muxlive.adapter.LiveStreamAdapter;
import br.com.apkdoandroid.muxlive.api.MuxApiService;
import br.com.apkdoandroid.muxlive.api.RetrofitClient;
import br.com.apkdoandroid.muxlive.databinding.ActivityHomeBinding;
import br.com.apkdoandroid.muxlive.entities.LiveStream;
import br.com.apkdoandroid.muxlive.entities.LiveStreamRequest;
import br.com.apkdoandroid.muxlive.entities.LiveStreamResponse;
import br.com.apkdoandroid.muxlive.entities.LiveStreamsResponse;
import br.com.apkdoandroid.muxlive.entities.NewAssetSettingsBean;
import br.com.apkdoandroid.muxlive.helper.Constantes;
import br.com.apkdoandroid.muxlive.interfaces.OnLiveStream;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private MuxApiService service = RetrofitClient.classService(MuxApiService.class);
    private ActivityHomeBinding binding;
    private LiveStreamAdapter adapter = new LiveStreamAdapter();
    private MainActivity.Preset preset = MainActivity.Preset.hd_720p_30fps_3mbps;
    // Se estiver testando em um loop fechado, você não vai querer colar uma chave de stream todas as vezes.
    //Em vez disso, defina uma chave de transmissão estática abaixo.
    private static final String defaultStreamKey = "";
    private static final String TAG = "MuxLive";
    // Permissões necessárias para acessar a câmera, o microfone e o armazenamento
    private final String[] PERMISSIONS = {
            android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!hasPermissions(this, PERMISSIONS)) {
            Log.i(TAG, "Solicitando permissões");
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }

        binding.buttonCriarTransmissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarTransmissao();
            }
        });
        binding.buttonBuscarTransmissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTransmissoes();
            }
        });

        binding.buttonBuscarLives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LivesDisponiveisActivity.class);
                startActivity(intent);
            }
        });


        OnLiveStream listener = new OnLiveStream() {
            @Override
            public void onClickTransmitir(LiveStream liveStream) {
                //resolução da gravação
                preset = MainActivity.Preset.hd_1080p_30fps_5mbps;
                if (hasPermissions(HomeActivity.this, PERMISSIONS)){

                    String streamKey = liveStream.getStream_key();

                    // Use a chave de fluxo padrão conectada se ela existir e não houver nada na caixa de texto
                    if (streamKey.isEmpty() && !defaultStreamKey.isEmpty()) {
                        streamKey = defaultStreamKey;
                    }

                    if (streamKey.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "Insira uma chave de fluxo ou defina um padrão no código.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.intentExtraStreamKey, streamKey);
                        intent.putExtra(MainActivity.intentExtraPreset, preset);
                        startActivity(intent);
                    }

                } else {
                    Log.i(TAG, "Solicitando permissões");
                    ActivityCompat.requestPermissions(HomeActivity.this, PERMISSIONS, 1);
                    // Seria bom se pudéssemos passar imediatamente para a atividade de configuração quando o
                    // permissões alteradas, mas isso parece ser difícil. Isso vai fazer por agora.
                }
            }

            @Override
            public void onClickAssistir(LiveStream liveStream) {
                Intent intent = new Intent(HomeActivity.this, PlayLiveActivity.class);
                intent.putExtra("live", liveStream.getPlayback_ids().get(0).getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(LiveStream liveStream) {
                deletarTransmissao(liveStream.getId());
            }
        };
        adapter.attackListener(listener);


    }

    private void criarTransmissao(){

        LiveStreamRequest liveStreamRequest = new LiveStreamRequest();
        NewAssetSettingsBean newAssetSettingsBean = new NewAssetSettingsBean();

        newAssetSettingsBean.setPlayback_policy(Arrays.asList("public"));
        liveStreamRequest.setNew_asset_settings(newAssetSettingsBean);
        liveStreamRequest.setPlayback_policy(Arrays.asList("public"));

        Call<LiveStreamResponse> call = service.createLiveStream(liveStreamRequest);

        call.enqueue(new Callback<LiveStreamResponse>() {
            @Override
            public void onResponse(Call<LiveStreamResponse> call, Response<LiveStreamResponse> response) {
                Log.d(Constantes.TAG,"Sucesso: "+response.isSuccessful());
                Log.d(Constantes.TAG,"Code: "+response.code());
                Log.d(Constantes.TAG,response.body().toString());
            }

            @Override
            public void onFailure(Call<LiveStreamResponse> call, Throwable t) {
                Log.d(Constantes.TAG,"Erro: "+t.getMessage());
            }
        });
    }

    private void getTransmissoes(){

        Call<LiveStreamsResponse> call = service.getLiveStreams();

        call.enqueue(new Callback<LiveStreamsResponse>() {
            @Override
            public void onResponse(Call<LiveStreamsResponse> call, Response<LiveStreamsResponse> response) {
                Log.d(Constantes.TAG,"Sucesso: "+response.isSuccessful());
                Log.d(Constantes.TAG,"Code: "+response.code());
                Log.d(Constantes.TAG,response.body().toString());
                if(response.isSuccessful()){
                    adapter.attackLiveStrame(response.body().getData());
                    recyclewview();
                    Toast.makeText(HomeActivity.this, "Carregando transmissões.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LiveStreamsResponse> call, Throwable t) {
                Log.d(Constantes.TAG,"Erro: "+t.getMessage());
            }
        });
    }

    private void deletarTransmissao(String id){

        Call<Void> call = service.deleteLiveStream(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(Constantes.TAG,"Sucesso: "+response.isSuccessful());
                Log.d(Constantes.TAG,"Code: "+response.code());
                if(response.isSuccessful()){
                    Toast.makeText(HomeActivity.this, "Transmissão deletada", Toast.LENGTH_SHORT).show();
                    getTransmissoes();
                }else{
                    Toast.makeText(HomeActivity.this, "Cod: "+response.code()+" - "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(Constantes.TAG,"Erro: "+t.getMessage());
            }
        });


    }

    private void recyclewview(){
        binding.recyclerviewTransmissoes.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewTransmissoes.setAdapter(adapter);
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTransmissoes();
    }
}