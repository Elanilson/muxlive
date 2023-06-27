package br.com.apkdoandroid.muxlive;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.apkdoandroid.muxlive.databinding.ActivityConfigureBinding;

public class ConfigureActivity extends AppCompatActivity {
    private static final String TAG = "MuxLive";
    private MainActivity.Preset preset = MainActivity.Preset.hd_720p_30fps_3mbps;

    // Referências de elemento de interface do usuário
    private EditText streamKeyField;

    // Se estiver testando em um loop fechado, você não vai querer colar uma chave de stream todas as vezes.
    //Em vez disso, defina uma chave de transmissão estática abaixo.
    private static final String defaultStreamKey = "";

    private ActivityConfigureBinding binding;

    // Permissões necessárias para acessar a câmera, o microfone e o armazenamento
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfigureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // getSupportActionBar().setTitle("Configure RTMP Stream Key");
        streamKeyField = (EditText) findViewById(R.id.streamKeyField);


        if (!hasPermissions(this, PERMISSIONS)) {
            Log.i(TAG, "Solicitando permissões");
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }

    public void changeProfile(View view) {
        Log.i(TAG, "Mudando de Perfil");

        if(view.getId() == R.id.tm360p){
            preset = MainActivity.Preset.sd_360p_30fps_1mbps;
        }else if(view.getId() == R.id.P540p){
            preset = MainActivity.Preset.sd_540p_30fps_2mbps;
        }else if(view.getId() == R.id.P720p){
            preset = MainActivity.Preset.hd_720p_30fps_3mbps;
        }else if(view.getId() == R.id.P1080p){
            preset = MainActivity.Preset.hd_1080p_30fps_5mbps;
        }
       /* switch (view.getId()) {
            case R.id.tm360p:
                preset = MainActivity.Preset.sd_360p_30fps_1mbps;
                break;
            case  R.id.P540p:
                preset = MainActivity.Preset.sd_540p_30fps_2mbps;
                break;
            case  R.id.P720p:
                preset = MainActivity.Preset.hd_720p_30fps_3mbps;
                break;
            case  R.id.P1080p:
                preset = MainActivity.Preset.hd_1080p_30fps_5mbps;
                break;
        }*/



    }

    public void startCamera(View view) {
        Log.i(TAG, "Botão tocado");

        if (hasPermissions(this, PERMISSIONS)){

            String streamKey = streamKeyField.getText().toString();

            // Use a chave de fluxo padrão conectada se ela existir e não houver nada na caixa de texto
            if (streamKey.isEmpty() && !defaultStreamKey.isEmpty()) {
                streamKey = defaultStreamKey;
            }

            if (streamKey.isEmpty()) {
                Toast.makeText(ConfigureActivity.this, "Insira uma chave de fluxo ou defina um padrão no código.", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(ConfigureActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.intentExtraStreamKey, streamKey);
                intent.putExtra(MainActivity.intentExtraPreset, preset);
                startActivity(intent);
            }

        } else {
            Log.i(TAG, "Solicitando permissões");
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            // Seria bom se pudéssemos passar imediatamente para a atividade de configuração quando o
            // permissões alteradas, mas isso parece ser difícil. Isso vai fazer por agora.
        }
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
}
