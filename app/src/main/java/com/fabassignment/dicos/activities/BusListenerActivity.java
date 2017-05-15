package com.fabassignment.dicos.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.fabassignment.dicos.controller.Bus;

/**
 * Created by benifabrice on 5/13/17.
 */

public class BusListenerActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        Bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Bus.unregister(this);
    }

}
