package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.eduapps.edumage.oge_app.AudioTaskActivity.hideKeyboard;

public class MainActivity extends AppCompatActivity {

    final String NAME_KEY = "UserName";
    final String GRADE_KEY = "UserGrade";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean("appHasRunBefore", false)) {
            /* This is not the first run */
            setMainScreen();
        } else {
            /* Do first run stuff */
            setContentView(R.layout.greetings);

            final EditText nameField = findViewById(R.id.name_field);
            final EditText gradeField = findViewById(R.id.grade_field);
            applyTextListener(gradeField);
            final TextView comment = findViewById(R.id.comment);
            comment.setVisibility(View.INVISIBLE);
            Button startButton = findViewById(R.id.start);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = nameField.getText().toString();
                    String grade = gradeField.getText().toString();
                    if (name.equals("") || grade.equals("")) {
                        comment.setVisibility(View.VISIBLE);
                    } else {
                        editor.putString(NAME_KEY, name).apply();
                        editor.putInt(GRADE_KEY, Integer.parseInt(grade)).apply();
                        editor.putBoolean("appHasRunBefore", true).apply();
                        setMainScreen();
                    }
                }
            });
        }
    }

    private void setMainScreen() {
        setContentView(R.layout.main_screen);

        TextView trainingButton = findViewById(R.id.training);
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainingsActivity.class);
                startActivity(intent);
            }
        });

        TextView variantsButton = findViewById(R.id.variants);
        variantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VariantsActivity.class);
                startActivity(intent);
            }
        });

        TextView theoryButton = findViewById(R.id.theory);
        theoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TheoryActivity.class);
                startActivity(intent);
            }
        });

        ImageView profileButton = findViewById(R.id.user_icon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageView settingButton = findViewById(R.id.settings_icon);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void applyTextListener(final EditText answer) {
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    hideKeyboard(MainActivity.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
