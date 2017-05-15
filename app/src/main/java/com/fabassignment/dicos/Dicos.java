package com.fabassignment.dicos;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;

/**
 * Created by benifabrice on 5/13/17.
 */

public class Dicos extends Application {
    private static Dicos instance;

    public static Dicos getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Iconify.with(new MaterialModule())
                .with(new FontAwesomeModule());
    }
}
