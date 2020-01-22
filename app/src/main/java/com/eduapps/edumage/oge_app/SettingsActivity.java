package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_information);

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
