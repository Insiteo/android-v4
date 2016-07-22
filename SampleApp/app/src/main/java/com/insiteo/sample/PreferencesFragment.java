package com.insiteo.sample;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;

import com.insiteo.sample.common.PreferenceFragment;
import com.insiteo.sdk.INSPush;
import com.insiteo.sdk.Insiteo;
import com.insiteo.sdk.InsiteoDebug;
import com.insiteo.sdk.INSLogger;

/**
 * Created by Cyril on 11/02/2016.
 */
public class PreferencesFragment extends PreferenceFragment {
    private final static String TAG = "PreferencesFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference button = (Preference)findPreference(getString(R.string.preference_reset_button));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                INSPush.clean();
                INSLogger.d(TAG, "Reset Cache");
                return true;
            }
        });

        SwitchPreference switchButton = (SwitchPreference)findPreference("pushmodule");
        switchButton.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object isServiceEnabled) {
                boolean isServiceOn = ((Boolean) isServiceEnabled).booleanValue();
                if(isServiceOn) {
                    InsiteoDebug.start();
                } else {
                    Insiteo.stop();
                }
                return true;
            }
        });
    }


}