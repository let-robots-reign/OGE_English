package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MistakesAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> userAnswers;
    private HashMap<String, List<String>> explanations;
    private boolean[] correctness;

    public MistakesAdapter(Context context, List<String> answers, HashMap<String, List<String>> info,
                           boolean[] colors) {
        this.context = context;
        this.userAnswers = answers;
        this.explanations = info;
        this.correctness = colors;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.explanations.get(this.userAnswers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_expandable_item, null);
        }

        TextView detail = convertView.findViewById(R.id.text1);
        SpannableString spannableChildText = makeExplanationsBold(childText);
        detail.setText(spannableChildText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.explanations.get(this.userAnswers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.userAnswers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.userAnswers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_item, null);
        }

        TextView answer = convertView.findViewById(R.id.text2);
        answer.setTypeface(null, Typeface.BOLD);
        answer.setText(headerTitle);

        if (correctness[groupPosition]) {
            answer.setTextColor(context.getResources().getColor(R.color.right_answer));
        } else {
            answer.setTextColor(context.getResources().getColor(R.color.wrong_answer));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private SpannableString makeExplanationsBold(String text) {
        // start-end indices
        List<Integer> boldIndices = new ArrayList<>();
        SpannableString spanText = new SpannableString(TextUtils.join("", text.split("\\|")));
        // memorizing indices of "|" signs
        for (int i = 0; i < text.split("\\|").length; i++) {
            if (i % 2 == 1) {
                boldIndices.add(text.indexOf(text.split("\\|")[i]));
                boldIndices.add(text.indexOf(text.split("\\|")[i]) + text.split("\\|")[i].length());
            } else {
                if (boldIndices.size() > 0) {
                    spanText.setSpan(new StyleSpan(Typeface.BOLD),
                            boldIndices.get(i - 2) - boldIndices.size(),
                            boldIndices.get(i - 1) - boldIndices.size() + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return spanText;
    }
}
