package br.com.apkdoandroid.muxlive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.rtplibrary.rtmp.RtmpCamera1;
import net.ossrs.rtmp.ConnectCheckerRtmp;
import com.pedro.encoder.input.video.CameraHelper;
import com.pedro.rtplibrary.util.FpsListener;

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

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, ConnectCheckerRtmp, View.OnTouchListener {
    // Logging tag
    private static final String TAG = "MuxLive";

    // Ponto de entrada RTMP do Mux
    private static final String rtmpEndpoint = "rtmp://global-live.mux.com:5222/app/";

    // A configuração da outra atividade chega por meio de uma intenção com algumas chaves extras
    public static final String intentExtraStreamKey = "STREAMKEY";
    public static final String intentExtraPreset = "PRESET";

    // UI Element references
    private Button goLiveButton;
    private TextView bitrateLabel;
    private TextView fpsLabel;
    private SurfaceView surfaceView;

    private RtmpCamera1 rtmpCamera;
    private Boolean liveDesired = false;
    private String streamKey;
    private Preset preset;


    // Codificação de predefinições e perfis
    public enum Preset {

        hd_1080p_30fps_5mbps(5000 * 1024, 1920, 1080, 30),
        hd_720p_30fps_3mbps(3000 * 1024, 1280, 720, 30),
        sd_540p_30fps_2mbps(2000 * 1024, 960, 540, 30),
        sd_360p_30fps_1mbps(1000 * 1024, 640, 360, 30),
        ;

        Preset(int bitrate, int width, int height, int frameRate) {
            this.bitrate = bitrate;
            this.height = height;
            this.width = width;
            this.frameRate = frameRate;
        }

        public int bitrate;
        public int height;
        public int width;
        public int frameRate;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Exibir o botão "voltar" na barra superior
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicie o Surface View para a visualização da câmera
        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);

        // Vincular a rótulos e botões
        goLiveButton = (Button) findViewById(R.id.button);
        bitrateLabel = (TextView) findViewById(R.id.bitrateLabel);
        fpsLabel = (TextView) findViewById(R.id.fpslabel);

        // Configurar a câmera
        rtmpCamera = new RtmpCamera1(surfaceView, this);
        rtmpCamera.setReTries(1000); // Effectively retry forever

        // Ouça os eventos de alteração de FPS para atualizar o indicador de FPS
        FpsListener.Callback callback = new FpsListener.Callback() {
            @Override
            public void onFps(int fps) {
                Log.i(TAG, "FPS: " + fps);
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        fpsLabel.setText(fps + " fps");
                    }
                });
            }
        };
        rtmpCamera.setFpsListener(callback);

        // Defina a configuração RTMP da intenção que acionou esta atividade
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String streamKey = extras.getString(intentExtraStreamKey);
            Preset preset = (Preset) extras.getSerializable(intentExtraPreset);
            this.preset = preset;
            this.streamKey = streamKey;
            Log.i(TAG, "Stream Key: " + streamKey);
        }

        // Keep the screen active on the Broadcast Activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void goLiveClicked(View view) {
        Log.i(TAG, "Botão Go Live tocado");

        if (liveDesired) {
            // Chamar a função "stopStream" pode demorar um pouco, então isso acontece em um novo thread.
            goLiveButton.setText("Stopping...");
            new Thread(new Runnable() {
                public void run() {
                    rtmpCamera.stopStream();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            goLiveButton.setText("Go Live!");
                        }
                    });
                }
            }).start();
            liveDesired = false;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); // Desbloquear orientação
        } else {

            // Bloqueie a orientação para a orientação atual enquanto o fluxo estiver ativo
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            switch (rotation) {
                case Surface.ROTATION_90:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case Surface.ROTATION_180:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    break;
                case Surface.ROTATION_270:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    break;
                default:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
            }

            // Configure o stream usando a predefinição configurada
            rtmpCamera.prepareVideo(
                    preset.width,
                    preset.height,
                    preset.frameRate,
                    preset.bitrate,
                    2, // Intervalo de quadro-chave fixo de 2s
                    CameraHelper.getCameraOrientation(this)
            );
            rtmpCamera.prepareAudio(
                    128 * 1024, // 128kbps
                    48000, // 48k
                    true // Stereo
            );

            // Inicie a transmissão!
            rtmpCamera.startStream(rtmpEndpoint + streamKey);
            liveDesired = true;
            goLiveButton.setText("Conectando... (Cancelar)");
        }
    }

    //Alterna entre a câmera frontal e traseira
    public void changeCameraClicked(View view) {
        Log.i(TAG, "Alterar botão da câmera tocado");
        rtmpCamera.switchCamera();
    }

    // Pequeno invólucro para realocar e refazer a torrada um pouco
    private void muxToast(String message) {
        Toast t = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 5);
        t.show();
    }

    // Chamadas do Surfaceview
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        // Pare a visualização se estiver em execução
        rtmpCamera.stopPreview();

        //Restringir um pouco o layout se a rotação do aplicativo mudou
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        ConstraintLayout.LayoutParams l = (ConstraintLayout.LayoutParams) surfaceView.getLayoutParams();
        switch (rotation) {
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                l.dimensionRatio = "w,16:9";
                break;
            default:
                l.dimensionRatio = "h,9:16";
                break;
        }
        surfaceView.setLayoutParams(l);

        //Reinicie a visualização, o que também redefinirá a rotação da visualização
        rtmpCamera.startPreview(1920, 1080);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
    }

    // Retornos de chamada do verificador RTMP
    @Override
    public void onConnectionSuccessRtmp() {
        goLiveButton.setText("Pare de transmitir!");
        Log.i(TAG, "Conexão RTMP com sucesso");
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                findViewById(R.id.textView2AoVivo).setVisibility(View.VISIBLE);
                muxToast("Conexão RTMP bem-sucedida!");
            }
        });
    }

    @Override
    public void onConnectionFailedRtmp(@NonNull String reason) {
        Log.w(TAG, "Falha na Conexão RTMP");
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                goLiveButton.setText("Reconectando... (Cancelar)");
                findViewById(R.id.textView2AoVivo).setVisibility(View.GONE);
                muxToast("Falha na conexão RTMP: " + reason);
            }
        });

        // Repetir falhas de conexão RTMP a cada 5 segundos
        rtmpCamera.reTry(5000, reason);
    }

    @Override
    public void onNewBitrateRtmp(long bitrate) {
        Log.d(TAG, "Taxa de bits RTMP alterada: " + (bitrate / 1024) );
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                bitrateLabel.setText(bitrate / 1024 + " kbps");
            }
        });
    }

    @Override
    public void onDisconnectRtmp() {
        Log.i(TAG, "RTMP Desconectar");
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                findViewById(R.id.textView2AoVivo).setVisibility(View.GONE);
                bitrateLabel.setText("0 kbps");
                fpsLabel.setText("0 fps");
                muxToast("RTMP desconectado!");
            }
        });
    }

    // onAuthErrorRtmp e onAuthSuccessRtmp não são usados se você estiver usando autenticação baseada em chave de transmissão
    @Override
    public void onAuthErrorRtmp() {
    }

    @Override
    public void onAuthSuccessRtmp() {
    }

    // Touch Listener Callbacks
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }




}
