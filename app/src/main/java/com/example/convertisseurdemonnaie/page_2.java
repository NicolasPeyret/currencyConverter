package com.example.convertisseurdemonnaie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class page_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("strOfConvertion")) {
                String str = intent.getStringExtra("strOfConvertion");

                TextView textView = (TextView) findViewById(R.id.tv1);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.infini_italique);
                textView.setTypeface(typeface);
                textView.setText(str);
            }
        }
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void click_quit(View v) {

        this.finishAffinity();


    }


    public void click_back(View v) {

        finish();
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1langue:
                Intent changerLangue = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changerLangue);
                return true;
            case R.id.menu1param:
                Intent changerParametres = new Intent(Settings.ACTION_SETTINGS);
                startActivity(changerParametres);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}