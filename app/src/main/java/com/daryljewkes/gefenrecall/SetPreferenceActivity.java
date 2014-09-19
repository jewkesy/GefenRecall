package com.daryljewkes.gefenrecall;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Daryl on 19/09/2014.
 */
public class SetPreferenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }
}
