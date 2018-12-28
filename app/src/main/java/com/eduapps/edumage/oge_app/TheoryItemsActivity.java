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

        String[] writingCards = new String[]{"Алгоритм написания", "Фразы-клише", "Слова-связки",
        "Полный ответ на вопрос", "Тренинг"};
        List<String> items = new ArrayList<>(Arrays.asList(writingCards));

        GridView grid = findViewById(R.id.gridview);
        GridViewAdapter adapter = new GridViewAdapter(items, this);
        grid.setAdapter(adapter);
    }
}
