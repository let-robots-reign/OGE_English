package com.eduapps.edumage.oge_app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

public class VariantTask extends AppCompatActivity {
    private int number;
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

        CategoryInVariantsAdapter adapter = new CategoryInVariantsAdapter(getSupportFragmentManager(), number);
        viewpager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewpager);
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
