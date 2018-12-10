package com.eduapps.edumage.oge_app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

        db = new DbHelper(this).getReadableDatabase();

        // getting the number of the variant
        Bundle extras = getIntent().getExtras();
        number = 0;
        if (extras != null) {
            number = extras.getInt("number");
        }

        setTitle("Вариант " + (number + 1));

        ViewPager viewpager = findViewById(R.id.viewpager);
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

        Button checkButton = findViewById(R.id.check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TaskFragment) fragments.get(0)).checkAudio0_1();
                ((TaskFragment) fragments.get(1)).checkAudio0_1();
                ((TaskFragment) fragments.get(2)).checkAudio2();
                ((TaskFragment) fragments.get(3)).checkReading0();
                ((TaskFragment) fragments.get(4)).checkReading1();
                ((TaskFragment) fragments.get(5)).checkUoe();
                ((TaskFragment) fragments.get(6)).checkUoe();
            }
        });
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
}
