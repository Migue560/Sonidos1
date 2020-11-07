package com.example.sonidos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.Builder;
import android.media.VolumeShaper;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.MediaController.MediaPlayerControl;

import static android.media.AudioManager.STREAM_MUSIC;
import static android.media.AudioManager.STREAM_SYSTEM;
import static android.media.MediaPlayer.create;

public class MainActivity extends AppCompatActivity {

    private SoundPool sPool;
    private int sound1, sound2,sound3,sound4;
    private MediaPlayer player;
    private AudioManager audioManager;
    private Button boton03, boton04;
    private SeekBar volumen,Valor;
    private TextView total, asonado,Info;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton03 = findViewById(R.id.button3);
        boton04 = findViewById(R.id.button4);
        Info = findViewById(R.id.textView3);
        total = findViewById(R.id.textView4);
        asonado = findViewById(R.id.textView5);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes attributes =new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            sPool=new Builder().setAudioAttributes(attributes).setMaxStreams(10).build();
        }
        else{
           sPool=new SoundPool(6, STREAM_MUSIC,0);
            }

        sound1=sPool.load(this,R.raw.gallina,1);
        sound2=sPool.load(this,R.raw.perro,1);
        sound3=sPool.load(this,R.raw.leon,1);
        sound4=sPool.load(this,R.raw.serpiente,1);
audioManager=(AudioManager) getSystemService((AUDIO_SERVICE));
volumen=findViewById(R.id.seekBar);
Valor=findViewById(R.id.seekBar2);

volumen.setMax((audioManager.getStreamMaxVolume(STREAM_MUSIC)));
volumen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        audioManager.setStreamVolume(STREAM_MUSIC,progress,0);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Info.setText(String.valueOf(player.getTrackInfo()));
        total.setText(String.valueOf(player.getDuration()));
        asonado.setText(String.valueOf(player.getCurrentPosition()));
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
    }

    public void suenaboton01(View v){
        sPool.play(sound1,1,1,1,0,1);
    }

    public void suenaboton02(View v){
        sPool.play(sound2,1,1,1,1,1);
    }
    public void suenaboton03(View v){
        if(player != null && player.isPlaying()){
            player.stop();
            boton03.setText("Reproducir mediaplayer");
        }
        else{
            player= create(this,R.raw.pharrell_williams_happy);
            total.setText(String.valueOf(player.getDuration()));
            player.start();
            boton03.setText("Detener mediaplayer");
        }
    }
    public void suenaboton04(View v){
        if(player != null){
            if(player.isPlaying()){
                player.pause();
                asonado.setText(String.valueOf(player.getCurrentPosition()));
                boton04.setText("Reiniciar");
            }
            else {
                player.start();
                boton04.setText("pausar");
            }
        }
    }
    public void suenaboton05(View v){
        sPool.play(sound3,1,1,1,0,1);
    }

    public void suenaboton06(View v){
        sPool.play(sound4,1,1,1,1,1);
    }
    public void run() {
        int currentPosition= 0;
        int total = player.getDuration();
        while (player!=null && currentPosition<total) {
            try {
                Thread.sleep(1000);
                currentPosition= player.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
            Valor.setProgress(currentPosition);
        }
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        sPool.release();
        sPool=null;
        player.release();
        player=null;
    }
}