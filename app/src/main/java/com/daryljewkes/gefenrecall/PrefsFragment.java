package com.daryljewkes.gefenrecall;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Daryl on 19/09/2014.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
