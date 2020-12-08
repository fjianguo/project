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
import com.example.weibohotsearchactivity.damain.Weibo;

import java.util.List;

public class WeiboAdapter extends ArrayAdapter {
    private final int resourceId;

    public WeiboAdapter(@NonNull Context context, int resource, @NonNull List<Weibo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Weibo weibo = (Weibo) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView textRank = view.findViewById(R.id.rank);
        TextView textTitle = view.findViewById(R.id.title);
        TextView textView = view.findViewById(R.id.view);
        textRank.setText(weibo.getRank());
        textTitle.setText(weibo.getTitle());
        textView.setText(weibo.getView());
        return view;
    }
}
