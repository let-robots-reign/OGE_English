package com.eduapps.edumage.oge_app;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.eduapps.edumage.oge_app.VariantsTasks.AudioTaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.ReadingTaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.TaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.UoeTaskFragment;

import java.util.ArrayList;
import java.util.List;

public class VariantTask extends AppCompatActivity {
    private static int number;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.variant);

        // instruction
        View view = getLayoutInflater().inflate(R.layout.dont_show_checkbox, null);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    storeDialogStatus(true);
                } else {
                    storeDialogStatus(false);
                }
            }
        });

        // when a user enters, he should see an instruction to the task
        AlertDialog.Builder builder = new AlertDialog.Builder(VariantTask.this);
        builder.setTitle("Инструкция")
                .setCancelable(false)
                .setMessage("Вариант содержит задания 1-32 письменной части экзамена. После " +
                        "выполнения всех заданий вы можете нажать на кнопку \"Проверить вариант\" " +
                        "и получить свой результат. После проверки варианта вы не сможете " +
                        "редактировать свои ответы, но сможете просмотреть свои ошибки.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setView(view);
        AlertDialog alert = builder.create();
        if (getDialogStatus()) {
            alert.hide();
        } else {
            alert.show();
        }

        db = new DbHelper(this).getReadableDatabase();

        // getting the number of the variant
        Bundle extras = getIntent().getExtras();
        number = 0;
        if (extras != null) {
            number = extras.getInt("number");
        }

        setTitle("Вариант " + (number + 1));

        final ViewPager viewpager = findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(7);

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(createAudioFragment(0));
        fragments.add(createAudioFragment(1));
        fragments.add(createAudioFragment(2));
        fragments.add(createReadingFragment(0));
        fragments.add(createReadingFragment(1));
        fragments.add(createUoeFragment(0));
        fragments.add(createUoeFragment(1));

        CategoryInVariantsAdapter adapter = new CategoryInVariantsAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewpager);

        final Button checkButton = findViewById(R.id.check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton.setVisibility(View.GONE);

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewpager.getLayoutParams();
                lp.setMargins(0, 0, 0, 0);
                viewpager.setLayoutParams(lp);

                int totalRightAnswers = 0;
                totalRightAnswers += ((TaskFragment) fragments.get(0)).checkAudio0_1();
                totalRightAnswers += ((TaskFragment) fragments.get(1)).checkAudio0_1();
                totalRightAnswers += ((TaskFragment) fragments.get(2)).checkAudio2();
                totalRightAnswers += ((TaskFragment) fragments.get(3)).checkReading0();
                totalRightAnswers += ((TaskFragment) fragments.get(4)).checkReading1();
                totalRightAnswers += ((TaskFragment) fragments.get(5)).checkUoe();
                totalRightAnswers += ((TaskFragment) fragments.get(6)).checkUoe();

                recordVariantResult(totalRightAnswers);

                AlertDialog.Builder builder = new AlertDialog.Builder(VariantTask.this);
                builder.setTitle("Ваш результат:")
                        .setCancelable(false)
                        .setMessage("You have " + totalRightAnswers + "/45 right answers")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
    }

    private void storeDialogStatus(boolean isChecked) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Dont_show_variant", isChecked);
        editor.apply();
    }

    private boolean getDialogStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("Dont_show_variant", false);
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    private static AudioTaskFragment createAudioFragment(int position) {
        AudioTaskFragment fragment = new AudioTaskFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("number", number);  // number of the variant
        bundle.putInt("position", position);  // type of the task
        fragment.setArguments(bundle);
        return fragment;
    }

    private static ReadingTaskFragment createReadingFragment(int position) {
        ReadingTaskFragment fragment = new ReadingTaskFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("number", number);  // number of the variant
        bundle.putInt("position", position);  // type of the task
        fragment.setArguments(bundle);
        return fragment;
    }

    private static UoeTaskFragment createUoeFragment(int position) {
        UoeTaskFragment fragment = new UoeTaskFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("number", number);  // number of the variant
        bundle.putInt("position", position);  // type of the task
        fragment.setArguments(bundle);
        return fragment;
    }

    private void recordVariantResult(int res) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains("Variant_" + (number + 1))) {
            editor.putInt("Variant_" + (number + 1), res);
        }
        editor.apply();
    }
}
