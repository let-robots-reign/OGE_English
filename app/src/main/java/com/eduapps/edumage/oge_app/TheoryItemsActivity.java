package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
        String[] categories = new String[]{"Общая информация об экзамене", "Языковой материал", "Письмо"};
        setTitle(categories[category]);

        setContentView(R.layout.theory_cards);

        String[] cards;
        List<String> items;
        switch (category) {
            case 0:
                cards = new String[]{"Порядок проведения", "Аудирование", "Чтение", "Языковой материал",
                                    "Письмо"};
                break;
            case 1:
                cards = new String[]{"Множественное число существительных", "Порядковые числительные",
                    "Притяжательные местоимения", "Объектные местоимения", "Возвратные местоимения",
                    "Пассивный залог", "I wish + V2", "Условное предложение (реальное)",
                    "Условное предложение (нереальное)", "Формы глагола to be", "Модальные глаголы",
                    "Настоящее простое", "Настоящее продолженное", "Настоящее совершённое",
                    "Прошедшее простое", "Прошедшее продолженное", "Прошедшее совершённое",
                    "Будущее простое", "Степени сравнения прилагательных", "Would + V"};
                break;
            case 2:
                cards = new String[]{"Алгоритм написания", "Фразы-клише", "Слова-связки"};
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
