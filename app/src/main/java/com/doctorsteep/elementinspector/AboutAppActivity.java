package com.doctorsteep.elementinspector;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctorsteep.elementinspector.adapter.AboutAppAdapter;
import com.doctorsteep.elementinspector.adapter.HelpAdapter;
import com.doctorsteep.elementinspector.adapter.WebEIConsoleAdapter;
import com.doctorsteep.elementinspector.model.AboutAppModel;
import com.doctorsteep.elementinspector.model.HelpModel;

import java.util.ArrayList;

public class AboutAppActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageIcon;
    private TextView textAppName, textAppVersion;
    private RecyclerView listAboutApp;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private ArrayList<AboutAppModel> list = new ArrayList<>();

    private PackageInfo pInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        // Id's
        toolbar = findViewById(R.id.toolbar);
        imageIcon = findViewById(R.id.imageApp);
        textAppName = findViewById(R.id.textAppName);
        textAppVersion = findViewById(R.id.textAppVersion);
        listAboutApp = findViewById(R.id.listAboutApp);

        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {}

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
        if (imageIcon != null) {
            imageIcon.setImageResource(R.mipmap.ic_launcher);
        }
        if (textAppName != null) {
            textAppName.post(new Runnable() {
                @Override
                public void run() {
                    textAppName.setText(getString(R.string.app_name_full));
                }
            });
        }
        if (textAppVersion != null) {
            textAppVersion.post(new Runnable() {
                @Override
                public void run() {
                    textAppVersion.setText(pInfo.versionName);
                }
            });
        }

        list.add(new AboutAppModel(getString(R.string.about_name_alexander_mikhno), getString(R.string.about_work_developer), "https://vk.com/doctorsteep"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listLayoutManager = new LinearLayoutManager(getApplicationContext());
                listAboutApp.setLayoutManager(listLayoutManager);
                listAdapter = new AboutAppAdapter(list, getApplicationContext());
                listAboutApp.setAdapter(listAdapter);
            }
        }, 300);
    }
}