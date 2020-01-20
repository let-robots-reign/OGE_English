package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.eduapps.edumage.oge_app.VariantsTasks.TaskFragment;

import java.util.List;

public class VariantTask extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Fragment>> {
    private static int number;
    private static SQLiteDatabase db;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.variant);

        db = new DbHelper(this).getReadableDatabase();

        // getting the number of the variant
        Bundle extras = getIntent().getExtras();
        number = 0;
        if (extras != null) {
            number = extras.getInt("number");
        }

        setTitle("Вариант " + (number + 1));
        Button checkButton = findViewById(R.id.check_button);
        checkButton.setVisibility(View.GONE);

        for (int i = 0; i < 10; i++) {
            getLoaderManager().destroyLoader(i);
        }
        getLoaderManager().restartLoader(number, null, this).forceLoad();
        getLoaderManager().initLoader(number, null, this).forceLoad();
    }

    @Override
    public Loader<List<Fragment>> onCreateLoader(int id, Bundle args) {
        ProgressBar bar = findViewById(R.id.loading_bar);
        bar.setVisibility(ProgressBar.VISIBLE);
        return new VariantLoader(this, number);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Fragment>> loader, List<Fragment> data) {
        ProgressBar bar = findViewById(R.id.loading_bar);
        bar.setVisibility(ProgressBar.GONE);

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
                .setCancelable(true)
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

        final ViewPager viewpager = findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(7);

        fragments = data;

        CategoryInVariantsAdapter adapter = new CategoryInVariantsAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewpager);
        int deviceHeight = getDeviceHeight();
        if (deviceHeight < 1000) {  // supporting small screens
            tabLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, 42));
        }

        final Button checkButton = findViewById(R.id.check_button);
        checkButton.setVisibility(View.VISIBLE);
        if (deviceHeight < 1000) {  // supporting small screens
            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 48);
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            checkButton.setLayoutParams(buttonParams);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewpager.getLayoutParams();
            lp.setMargins(0, 0, 0, 48);
            viewpager.setLayoutParams(lp);
        }
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
                        .setNegativeButton("Смотреть ошибки и ответы", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // the same behavior for the "up/home" button in the Action Bar
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Fragment>> loader) {
        getLoaderManager().destroyLoader(loader.getId());
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

    private int getDeviceHeight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
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
