package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        int[] topics;
        switch (category) {
            case 0:
                cards = new String[]{"Порядок проведения", "Аудирование", "Чтение", "Языковой материал",
                        "Письмо"};
                topics = new int[]{R.string.procedure, R.string.audio_instruction,
                        R.string.reading_instruction, R.string.uoe_instruction, R.string.writing_instruction};
                break;
            case 1:
                cards = new String[]{"Множественное число существительных", "Порядковые числительные",
                        "Притяжательные местоимения", "Объектные местоимения", "Возвратные местоимения",
                        "Пассивный залог", "I wish + V2", "Условное предложение (реальное)",
                        "Условное предложение (нереальное)", "Формы глагола to be", "Модальные глаголы",
                        "Настоящее простое", "Настоящее продолженное", "Настоящее совершённое",
                        "Прошедшее простое", "Прошедшее продолженное", "Прошедшее совершённое",
                        "Будущее простое", "Степени сравнения прилагательных", "Would + V"};
                topics = new int[]{R.string.plurals_html, R.string.numerals_html, R.string.possessive_html,
                                    R.string.objective_html, R.string.self_html, R.string.passive_html_part_1,
                                    R.string.wish_html, R.string.if_real_html, R.string.if_unreal_html,
                                    R.string.to_be_html, R.string.modals_html, R.string.clarification,
                                    R.string.clarification, R.string.clarification, R.string.clarification, R.string.clarification,
                                    R.string.clarification, R.string.clarification, R.string.degrees_comparison_html,
                                    R.string.would_html_part_1};
                break;
            case 2:
                cards = new String[]{"Алгоритм написания", "Фразы-клише", "Слова-связки"};
                topics = new int[]{R.string.algorythm, R.string.blank, R.string.objective_html};
                break;
            default:
                cards = new String[]{""};  // empty for now
                topics = new int[]{};
        }

        String topic = cards[position];
        setTitle(topic);
        setContentView(R.layout.theory_page);

        LinearLayout layout = findViewById(R.id.theory_layout);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 32, 0, 32);

        TextView heading = findViewById(R.id.theory_heading);
        heading.setText(topic);
        TextView main = findViewById(R.id.main_theory_block);
        main.setText(topics[position]);

        if (category == 1) {
            if (position == 2) {
                View view = getLayoutInflater().inflate(R.layout.possessive_pronouns_table, layout, false);
                layout.addView(view);
            } else if (position == 3) {
                View view = getLayoutInflater().inflate(R.layout.objective_pronouns_table, layout, false);
                layout.addView(view);
            } else if (position == 4) {
                View view = getLayoutInflater().inflate(R.layout.self_pronouns_table, layout, false);
                layout.addView(view);
            } else if (position == 5) {
                View view = getLayoutInflater().inflate(R.layout.passive_table, layout, false);
                layout.addView(view);

                TextView text = new TextView(this);
                text.setText(R.string.passive_html_part_2);
                text.setTextSize(16);
                text.setTextColor(getResources().getColor(R.color.colorPrimaryText));

                layout.addView(text);
            } else if (position == 9) {
                View view = getLayoutInflater().inflate(R.layout.passive_table, layout, false);
                layout.addView(view);
            } else if (position == 11) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.present_simple_table, layout, false);
                layout.addView(view);
            } else if (position == 12) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.present_continuous_table, layout, false);
                layout.addView(view);
            } else if (position == 13) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.present_perfect_table, layout, false);
                layout.addView(view);
            } else if (position == 14) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.past_simple_table, layout, false);
                layout.addView(view);
            } else if (position == 15) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.past_continuous_table, layout, false);
                layout.addView(view);
            } else if (position == 16) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.past_perfect_table, layout, false);
                layout.addView(view);
            } else if (position == 17) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.future_simple_table, layout, false);
                layout.addView(view);
            } else if (position == 19) {
                main.setPadding(32, 32, 32, 32);
                layout.setLayoutParams(lp);
                View view = getLayoutInflater().inflate(R.layout.reported_speech_table, layout, false);
                layout.addView(view);
                TextView text = new TextView(this);
                text.setText(R.string.would_html_part_2);
                text.setTextSize(16);
                text.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                text.setPadding(32, 32, 32, 32);
                layout.addView(text);
            }
        } else if (category == 2) {
            if (position == 1) {
                setContentView(R.layout.cliches_theory);
            } else if (position == 2) {
                layout.setLayoutParams(lp);
                main.setTextSize(5);
                main.setVisibility(View.INVISIBLE);
                View view = getLayoutInflater().inflate(R.layout.linkers_table, layout, false);
                layout.addView(view);
            }
        }
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
