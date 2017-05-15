package com.fabassignment.dicos.activities;

import android.support.design.widget.NavigationView;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fabassignment.dicos.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by benifabrice on 5/14/17.
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureNavigationViewIspresent() {
        final MainActivity activity = rule.getActivity();
        View navigationView = activity.findViewById(R.id.nav_drawer);
        assertThat(navigationView, notNullValue());
        assertThat(navigationView, instanceOf(NavigationView.class));
        NavigationView view = (NavigationView) navigationView;
        assertThat("should have one header", view.getHeaderCount() == 1);
        assertThat("should be opened at start up ", view.isShown());
        assertThat("should have one section", view.getChildCount() == 1);
    }

    @Test
    public void ensureSearchViewIspresent() {
        final MainActivity activity = rule.getActivity();
        View toolbar = activity.findViewById(R.id.toolbar);
        assertThat(toolbar, notNullValue());
        assertThat(toolbar, instanceOf(Toolbar.class));
    }


}
