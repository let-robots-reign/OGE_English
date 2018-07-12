package com.eduapps.edumage.oge_app;

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
        setContentView(R.layout.variants_list);

        RecyclerView variants_list = findViewById(R.id.variants_list);

        variants_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        variants_list.setLayoutManager(layoutManager);

        List<VariantCard> variants = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            variants.add(new VariantCard(i, readIfSolvedFromDB(i)));
        }

        RVVariantsAdapter adapter = new RVVariantsAdapter(variants);
        variants_list.setAdapter(adapter);
    }

    /** read if user completed the variant #number from the database */
    public boolean readIfSolvedFromDB(int number) {
        // TODO: read from DB
        return (number >= 2 && number <= 6);
    }
}
