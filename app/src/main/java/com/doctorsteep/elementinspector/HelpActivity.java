package com.doctorsteep.elementinspector;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctorsteep.elementinspector.adapter.HelpAdapter;
import com.doctorsteep.elementinspector.adapter.WebEIConsoleAdapter;
import com.doctorsteep.elementinspector.model.HelpModel;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView listHelp;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private ArrayList<HelpModel> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        // Id's
        toolbar = findViewById(R.id.toolbar);
        listHelp = findViewById(R.id.listHelp);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        list.add(new HelpModel(getString(R.string.action_what_app), getString(R.string.message_what_app)));
        list.add(new HelpModel(getString(R.string.action_console), getString(R.string.message_console)));
        list.add(new HelpModel(getString(R.string.action_contenteditable), getString(R.string.message_contenteditable)));
        list.add(new HelpModel(getString(R.string.action_highlight_border), getString(R.string.message_highlight_border)));
        list.add(new HelpModel(getString(R.string.action_source_page), getString(R.string.message_source_page)));
        list.add(new HelpModel(getString(R.string.action_debug_mode), getString(R.string.message_debug_mode)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listLayoutManager = new LinearLayoutManager(getApplicationContext());
                listHelp.setLayoutManager(listLayoutManager);
                listAdapter = new HelpAdapter(list, getApplicationContext());
                listHelp.setAdapter(listAdapter);
            }
        }, 300);
    }
}