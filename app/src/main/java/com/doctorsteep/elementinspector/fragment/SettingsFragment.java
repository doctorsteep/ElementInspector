package com.doctorsteep.elementinspector.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import com.doctorsteep.elementinspector.AboutAppActivity;
import com.doctorsteep.elementinspector.R;
import com.doctorsteep.elementinspector.RemoveAdsActivity;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference keyAboutApp = findPreference("keyAboutApp");
        Preference keyRemoveAds = findPreference("keyRemoveAds");
        Preference keyViewBottomMenuBar = findPreference("keyViewBottomMenuBar");
        final Preference keyViewBottomMenuBarButtons = findPreference("keyViewBottomMenuBarButtons");

        if (keyViewBottomMenuBar.getSharedPreferences().getBoolean("keyViewBottomMenuBar", false)) {
            keyViewBottomMenuBarButtons.setEnabled(true);
        } else {
            keyViewBottomMenuBarButtons.setEnabled(false);
        }

        keyAboutApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startActivity(new Intent(getActivity(), AboutAppActivity.class));
                return false;
            }
        });
        keyRemoveAds.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startActivity(new Intent(getActivity(), RemoveAdsActivity.class));
                return false;
            }
        });

        keyViewBottomMenuBar.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getSharedPreferences().getBoolean("keyViewBottomMenuBar", false)) {
                    keyViewBottomMenuBarButtons.setEnabled(true);
                } else {
                    keyViewBottomMenuBarButtons.setEnabled(false);
                }
                return false;
            }
        });
    }
}
