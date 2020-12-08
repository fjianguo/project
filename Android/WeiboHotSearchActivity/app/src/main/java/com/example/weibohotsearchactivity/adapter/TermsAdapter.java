package com.example.weibohotsearchactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weibohotsearchactivity.R;
import com.example.weibohotsearchactivity.damain.Terms;

import java.util.List;

public class TermsAdapter extends ArrayAdapter {
    private final int resourceId;

    public TermsAdapter(@NonNull Context context, int resource, @NonNull List<Terms> objects) {
        super(context,resource,objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Terms terms = (Terms) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView textRank = view.findViewById(R.id.rank);
        TextView textTitle = view.findViewById(R.id.title);
        TextView textView = view.findViewById(R.id.view);
        textRank.setText(terms.getId());
        textTitle.setText(terms.getTerm());
        textView.setText(terms.getNumber());
        return view;
    }
}
