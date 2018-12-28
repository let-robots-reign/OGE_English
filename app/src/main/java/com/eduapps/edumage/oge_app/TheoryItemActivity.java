package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class TheoryItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int category = 0;
        int position = 0;
        if (extras != null) {
            category = extras.getInt("category");
            position = extras.getInt("position");
        }

        String[] cards;
        int[] layouts;
        switch (category) {
            case 0:
                layouts = new int[]{};
                cards = new String[]{""};  // empty for now
                break;
            case 1:
                layouts = new int[]{};
                cards = new String[]{""};  // empty for now
                break;
            case 2:
                layouts = new int[]{R.layout.uoe_theory_plural_nouns};
                cards = new String[]{"Множ. число сущ."};
                break;
            case 3:
                layouts = new int[]{R.layout.writing_theory_algo, R.layout.writing_theory_algo,
                        R.layout.writing_theory_algo, R.layout.writing_theory_algo,
                        R.layout.writing_theory_algo};
                cards = new String[]{"Алгоритм написания", "Фразы-клише", "Слова-связки",
                        "Полный ответ на вопрос", "Тренинг"};
                break;
            default:
                layouts = new int[]{};
                cards = new String[]{""};  // empty for now
        }
        
        setTitle(cards[position]);
        setContentView(layouts[position]);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
