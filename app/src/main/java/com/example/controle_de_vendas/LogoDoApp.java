package com.example.controle_de_vendas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LogoDoApp extends AppCompatActivity {
    private Timer timer = new Timer();
    private TimerTask timerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_do_app);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        passaProximaTela();
                    }
                });
            }
        };
        timer.schedule(timerTask,3000);
    }
    private void passaProximaTela() {
        Intent intent = new Intent(this,TelaPrincipal.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}