package com.eduapps.edumage.oge_app;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VariantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiate();
    }

    private void instantiate() {
        setContentView(R.layout.variants_list);

        RecyclerView variants_list = findViewById(R.id.variants_list);

        variants_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        variants_list.setLayoutManager(layoutManager);

        List<VariantCard> variants = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            variants.add(new VariantCard(i, readResultFromPreferences(i)));
        }

        RVVariantsAdapter adapter = new RVVariantsAdapter(variants, this);
        variants_list.setAdapter(adapter);
    }

    /** read the result of the variant #number from preferences */
    public int readResultFromPreferences(int number) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains("Variant_" + number)) {
            return preferences.getInt("Variant_" + number, -1);
        } else {
            editor.putInt("Variant_" + number, -1);
            editor.apply();
            return -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        instantiate();
    }
}
