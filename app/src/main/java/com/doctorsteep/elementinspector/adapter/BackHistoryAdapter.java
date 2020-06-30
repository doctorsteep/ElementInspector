package com.doctorsteep.elementinspector.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.doctorsteep.elementinspector.MainActivity;
import com.doctorsteep.elementinspector.R;
import com.doctorsteep.elementinspector.model.BackHistoryModel;
import com.doctorsteep.elementinspector.widget.WebEI;

public class BackHistoryAdapter extends RecyclerView.Adapter<BackHistoryAdapter.BackHistoryHolder> {

    private ArrayList<BackHistoryModel> list;
    private Context context;
    private WebEI web;

    public BackHistoryAdapter(ArrayList<BackHistoryModel> list, Context context, WebEI web) {
        this.list = list;
        this.context = context;
        this.web = web;
    }

    @Override
    public BackHistoryAdapter.BackHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_back_history, parent, false);
        BackHistoryHolder holder = new BackHistoryHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BackHistoryAdapter.BackHistoryHolder holder, int position) {
        final BackHistoryModel model = list.get(position);

        holder.textTitle.post(new Runnable() {
            @Override
            public void run() {
                if (!model.getTitle().equals("")) {
                    holder.textTitle.setText(model.getTitle());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl(model.getUrl());
            }
        });

        try {
            holder.iconBitmap.post(new Runnable() {
                @Override
                public void run() {
                    holder.iconBitmap.setImageBitmap(model.getIcon());
                }
            });
        } catch (Exception e) {
            holder.iconBitmap.post(new Runnable() {
                @Override
                public void run() {
                    holder.iconBitmap.setImageResource(R.drawable.ic_favicon_default);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BackHistoryHolder extends RecyclerView.ViewHolder {
        protected TextView textTitle;
        protected ImageView iconBitmap;

        public BackHistoryHolder(View item) {
            super(item);
            textTitle = item.findViewById(R.id.textTitle);
            iconBitmap = item.findViewById(R.id.iconBitmap);
        }
    }
}

