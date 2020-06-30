package com.doctorsteep.elementinspector.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.doctorsteep.elementinspector.R;
import com.doctorsteep.elementinspector.model.AboutAppModel;

public class AboutAppAdapter extends RecyclerView.Adapter<AboutAppAdapter.AboutAppHolder> {

    private ArrayList<AboutAppModel> list;
    private Context context;

    public AboutAppAdapter(ArrayList<AboutAppModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AboutAppAdapter.AboutAppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_about_app, parent, false);
        AboutAppHolder holder = new AboutAppHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AboutAppAdapter.AboutAppHolder holder, int position) {
        final AboutAppModel model = list.get(position);

        holder.textName.post(new Runnable() {
            @Override
            public void run() {
                holder.textName.setText(model.getName());
            }
        });
        holder.textWork.post(new Runnable() {
            @Override
            public void run() {
                holder.textWork.setText(model.getWork());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl())));
                } catch (Exception e) {}
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AboutAppHolder extends RecyclerView.ViewHolder {
        protected TextView textName;
        protected TextView textWork;

        public AboutAppHolder(View item) {
            super(item);
            textName = item.findViewById(R.id.textName);
            textWork = item.findViewById(R.id.textWork);
        }
    }
}



