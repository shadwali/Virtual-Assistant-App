package com.example.myassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Runnable runnable = new Runnable() {;
            @Override
            public void run() {
                getActivity();
                finish();
            }
        };
        Handler myhandler = new Handler();
        myhandler.postDelayed(runnable,3000);
    }

    public void getActivity() {
        Intent intent=new Intent(this,Assistant.class);
        startActivity(intent);
    }


}
