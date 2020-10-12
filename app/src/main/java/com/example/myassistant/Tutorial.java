package com.example.myassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Tutorial extends AppCompatActivity {

    Toolbar hlptool;
    TextView tutorialText;
    TextView first;
    TextView second;
    TextToSpeech textToSpeech;
    String text;
    String text1;
    String text2;
    int resultSpeech;
    boolean flag=true;
    boolean flag1=true;
    boolean flag2=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        hlptool = findViewById(R.id.tutorial);
        setSupportActionBar(hlptool);
        getSupportActionBar().setTitle("Tutorial");
        tutorialText = (TextView) findViewById(R.id.tutorialtext);
        text = tutorialText.getText().toString();

        first=(TextView)findViewById(R.id.first);
        text1=first.getText().toString();

        second=(TextView)findViewById(R.id.secondtext);
        text2=second.getText().toString();


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    resultSpeech = textToSpeech.setLanguage(Locale.getDefault());
                    flag = true;

                } else {
                    Toast.makeText(Tutorial.this, "Featture Not Supported in your Device", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (flag) {
            flag1 = tutorialSpeak(text);
        }else {
            Toast.makeText(this, "Voice feature not Supported", Toast.LENGTH_SHORT).show();
        }

        if (flag1) {
            flag2=tutorialSpeak(text1);
        }else {
            Toast.makeText(this, "Voice feature not Supported 1", Toast.LENGTH_SHORT).show();
        }
        if(flag2){
            flag=tutorialSpeak(text2);
        }else {
            Toast.makeText(this, "Voice feature not Supported 2", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean tutorialSpeak(String text) {
        boolean f=false;
        if(resultSpeech==TextToSpeech.LANG_NOT_SUPPORTED || resultSpeech==TextToSpeech.LANG_MISSING_DATA){
            Toast.makeText(this, "Feature Not Supported in your Device", Toast.LENGTH_SHORT).show();
            return false;

        }else{
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            Runnable runnable = new Runnable() {;
                @Override
                public void run() {

                }
            };
            Handler myhandler = new Handler();
            myhandler.postDelayed(runnable,5000);
            return true;
        }
      /*  if(f==true){
            return  true;
        }else {
            return false;
        }*/


}

    public void goBack(View view) {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech!=null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
