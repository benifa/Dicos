package com.fabassignment.dicos.fragment;

import android.support.v4.app.Fragment;

import com.fabassignment.dicos.controller.Bus;

/**
 * Created by benifabrice on 5/14/17.
 */

public class BusListenerFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bus.register(this);
    }

    @Override
    public void onStop() {
        Bus.unregister(this);
        super.onStop();
    }

}
