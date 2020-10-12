package com.example.myassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class About extends AppCompatActivity {

    Toolbar abtool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        abtool=findViewById(R.id.abouttool);
        setSupportActionBar(abtool);
        getSupportActionBar().setTitle("About");
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

}
