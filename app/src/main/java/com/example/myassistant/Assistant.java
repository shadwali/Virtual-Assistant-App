package com.example.myassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.net.Uri.*;

public class Assistant extends AppCompatActivity {


    private TextView tvResult1;

    TextToSpeech ttsobject;
    int result1;
    TextView et;
    String text;
    ArrayList<String> result;
    String str=new String();
    String s_str[] = new String[3];
    Cursor cursor;
    ContentResolver resolver;
    Toolbar toolbar;
    int resultSpeech;
    boolean flagSpeak=false;



    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant2);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Virtual Assistant");


        //Text to Speech Conversion
        et=(TextView)findViewById(R.id.textView);
        text=et.getText().toString();
        ttsobject=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    resultSpeech=ttsobject.setLanguage(Locale.getDefault());
                    speakSystem(text);

                }else{
                    Toast.makeText(Assistant.this, "Featture Not Supported in your Device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // this is used for print user input
        tvResult1 = (TextView) this.<View>findViewById(R.id.tvResult);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M&&
                    checkSelfPermission(Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 3);
            }
        resolver = getContentResolver();
        cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
    }


    //This method is to to Speak form System for first time only
    private void speakSystem(String textIn) {

        if(resultSpeech==TextToSpeech.LANG_NOT_SUPPORTED || resultSpeech==TextToSpeech.LANG_MISSING_DATA){
            Toast.makeText(this, "Feature Not Supported in your Device", Toast.LENGTH_SHORT).show();
        }else{
            ttsobject.speak(textIn,TextToSpeech.QUEUE_FLUSH,null);
            et.setText(textIn);
        }
    }

    //This method is used to by MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



    //This method is used for popup menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            /*case R.id.home:
                break;*/
            case R.id.help:
                Intent hlp=new Intent(this,Help.class);
                startActivity(hlp);
                break;
            case R.id.feedback:
                Intent fdback =  new Intent(this,Feedback.class);
                startActivity(fdback);
                break;
            case  R.id.about:
                Intent abut =new Intent(this,About.class);
                startActivity(abut);
                break;
            case  R.id.appinfo:
                Intent appinfo =new Intent(this,Appinfo.class);
                startActivity(appinfo);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return super.onOptionsItemSelected(item);
    }

    //This method is execute when user tap the image
    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say");

        if (intent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device don't support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    //This method is used to punch the user text on to the screen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode== RESULT_OK && data != null){
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvResult1.setText(result.get(0));
                    makePhoneCall();

                }
                break;
            default:
        }
    }

    private void makePhoneCall() {
        int flag = 0;
        String call = "call";
        str = result.get(0).toString();
        final Random myr= new Random();
        String myspeech= "my name is";

        s_str[0] = str.substring(0, 4);
        s_str[1] = str.substring(5, str.length());

        char ch[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < s_str[1].length(); i++) {
            try {
                flag = 0;
                for (int j = 0; j <= 9; j++) {
                    char ch1 = s_str[1].charAt(i);
                    if (ch[j] == ch1) {
                        flag = 1;
                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
            }
        }


        if(str.equalsIgnoreCase("Hello")){

            int n = myr.nextInt(4);
            switch (n){
                case 1:
                    speakSystem("Haan ji, How can i help you?");
                    break;
                case 2:
                    speakSystem("Hello ji, Is anything i can do for you? Please tell me.");
                    break;
                case 3:
                    speakSystem("Namaste, What can i do for you?");
                    break;
            }

        }


        if((str.equalsIgnoreCase("How are you")) || str.equalsIgnoreCase("How are you friday")){
            int n = myr.nextInt(3);
            switch (n){
                case 1:
                    speakSystem("I'm fine, thanks for asking.What can I help you with?");
                    break;
                case 2:
                    speakSystem("I'm doing great.How may I help you? ");
                    break;
            }

        }
        if(str.equalsIgnoreCase("Who are you")){
            speakSystem("I'm Friday your Virtual Assistant.I can help you by providing some services to you.Just ask... ");
        }

        if((str.toLowerCase()).contains(myspeech.toLowerCase())){
            speakSystem("Hi "+str.substring(11,str.length())+"...How can I help you?");
        }


        if (s_str[0].equalsIgnoreCase(call)) {
            if (flag == 1) {
                if (s_str[1].trim().length() > 0) {
                    speakSystem("Calling... "+s_str[1]);
                    makeCall(s_str[1]);
                } else {
                    Toast.makeText(this, "Say Phone Number to Call", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 2);

                }else {
                    makeContactCall();
                }
            }

        }
    }




    private void makeCall(String s) {
        if(ContextCompat.checkSelfPermission
                (this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.CALL_PHONE},1);

        }
        else {
            String dial = "tel:" + s;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    private void makeContactCall() {

        Cursor cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);


        while (cursor1.moveToNext()){

            String name = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String st1 =name.toLowerCase();
            String st2 = s_str[1].toLowerCase();
            Log.i("My:",name+" "+number);
            if(st1.contains(st2)){
                Log.i("My:",name+""+number);
                speakSystem("Calling "+name);
                makeCall(number);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
        }
        if(requestCode==2){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makeContactCall();
            }
        }
        if(requestCode==3){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ttsobject!=null){
            ttsobject.stop();
            ttsobject.shutdown();
        }
    }
}
