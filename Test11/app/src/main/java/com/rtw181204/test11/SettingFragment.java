package com.rtw181204.test11;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingFragment extends PreferenceFragment {


    @Override
    public void onCreate( Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.setting);
    }
}
