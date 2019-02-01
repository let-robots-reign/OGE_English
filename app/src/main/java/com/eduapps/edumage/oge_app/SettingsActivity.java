package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_information);

        AdView adView = findViewById(R.id.adView_info);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        ImageView emailButton = findViewById(R.id.email_icon);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] {"mobile.4.education@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "ОГЭ Английский");

                startActivity(Intent.createChooser(intent, "Email via..."));
            }
        });
    }
}
