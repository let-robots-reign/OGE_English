package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    List<String> source;
    Context context;

    public GridViewAdapter(List<String> source, Context c) {
        this.source = source;
        this.context = c;
    }

    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public Object getItem(int position) {
        return source.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Button button;
        if (convertView == null) {
            button = new Button(context);
            button.setPadding(8, 8,8 ,8);
            button.setText(source.get(position));
            button.setTextSize(12);
            button.setBackgroundColor(context.getResources().getColor(R.color.colorSecondary));
            button.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TheoryItemActivity.class);
                    intent.putExtra("layout", position);
                    context.startActivity(intent);
                }
            });
        } else {
            button = (Button) convertView;
        }

        return button;
    }
}
