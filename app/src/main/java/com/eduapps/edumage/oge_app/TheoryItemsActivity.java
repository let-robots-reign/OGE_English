package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TheoryItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }
        String[] categories = new String[]{"Аудирование", "Чтение", "Языковой материал", "Письмо"};
        setTitle(categories[category]);

        setContentView(R.layout.theory_cards);

        String[] cards;
        List<String> items;
        switch (category) {
            case 0:
                cards = new String[]{};  // empty for now
                break;
            case 1:
                cards = new String[]{};  // empty for now
                break;
            case 2:
                cards = new String[]{"Множ. число сущ."};
                break;
            case 3:
                cards = new String[]{"Алгоритм написания", "Фразы-клише", "Слова-связки",
                        "Полный ответ на вопрос", "Тренинг"};
                break;
            default:
                cards = null;
        }

        items = new ArrayList<>(Arrays.asList(cards));
        GridView grid = findViewById(R.id.gridview);
        GridViewAdapter adapter = new GridViewAdapter(items, category, this);
        grid.setAdapter(adapter);
    }
}
