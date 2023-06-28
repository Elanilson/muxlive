package br.com.apkdoandroid.muxlive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.apkdoandroid.muxlive.adapter.LiveDisponiveisAdapter;
import br.com.apkdoandroid.muxlive.adapter.LiveStreamAdapter;
import br.com.apkdoandroid.muxlive.api.MuxApiService;
import br.com.apkdoandroid.muxlive.api.RetrofitClient;
import br.com.apkdoandroid.muxlive.databinding.ActivityLivesDisponiveisBinding;
import br.com.apkdoandroid.muxlive.entities.LiveStream;
import br.com.apkdoandroid.muxlive.entities.LiveStreamsResponse;
import br.com.apkdoandroid.muxlive.helper.Constantes;
import br.com.apkdoandroid.muxlive.interfaces.OnLiveStream;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LivesDisponiveisActivity extends AppCompatActivity {
    private MuxApiService service = RetrofitClient.classService(MuxApiService.class);
    private LiveDisponiveisAdapter adapter = new LiveDisponiveisAdapter();
    private ActivityLivesDisponiveisBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivesDisponiveisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OnLiveStream listener = new OnLiveStream() {
            @Override
            public void onClickTransmitir(LiveStream liveStream) {

            }

            @Override
            public void onClickAssistir(LiveStream liveStream) {
                Intent intent = new Intent(LivesDisponiveisActivity.this, PlayLiveActivity.class);
                intent.putExtra("live", liveStream.getPlayback_ids().get(0).getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(LiveStream liveStream) {

            }
        };
        adapter.attackListener(listener);
    }

    private void recyclewview(){
        binding.recyclerViewLivesDisponiveis.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewLivesDisponiveis.setAdapter(adapter);
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

                    List<LiveStream> liveStreams = response.body().getData();
                    List<LiveStream> listaFiltrada = new ArrayList<>();
                    if(liveStreams != null){
                        for(LiveStream item : liveStreams){
                            if(item.getStatus().equals("active")){
                                binding.textViewInfoLive.setVisibility(View.GONE);
                                listaFiltrada.add(item);
                            }
                        }
                    }
                    adapter.attackLiveStrame(listaFiltrada);
                    recyclewview();
                    Toast.makeText(LivesDisponiveisActivity.this, "Carregando Lives.", Toast.LENGTH_SHORT).show();
                }else{
                    binding.textViewInfoLive.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LiveStreamsResponse> call, Throwable t) {
                Log.d(Constantes.TAG,"Erro: "+t.getMessage());
                binding.textViewInfoLive.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTransmissoes();
    }
}