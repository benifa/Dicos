package com.fabassignment.dicos.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fabassignment.dicos.R;
import com.fabassignment.dicos.controller.Bus;
import com.fabassignment.dicos.enums.DefinitionComparatorType;
import com.fabassignment.dicos.fragment.DefinitionListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by benifabrice on 5/12/17.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.nav_drawer)
    protected NavigationView mRefineView;

    private SearchView mSearchView;
    private String mSearchQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        mDrawerLayout.openDrawer(GravityCompat.END);
        mRefineView.setNavigationItemSelectedListener(this);
        loadListFragment(savedInstanceState);
        setResetClickListener();
        drawerToggle.syncState();
    }

    private void setResetClickListener() {
        final TextView resetButton = (TextView) mRefineView.getHeaderView(0).findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bus.sortDefinitionsEvent(DefinitionComparatorType.RESET);
                mDrawerLayout.closeDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save search Term into the bundle
        savedInstanceState.putString("SearchTerm", mSearchQuery);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore searchQuery from saved instance
        mSearchQuery = savedInstanceState.getString("SearchTerm");
    }


    private void loadListFragment(@Nullable Bundle savedInstanceState) {
        DefinitionListFragment fragment = null;
        if (savedInstanceState != null) { // saved instance state, fragment may exist
            // look up the instance that already exists by tag
            fragment =
                    (DefinitionListFragment) getSupportFragmentManager().findFragmentByTag("DefinitionListFragment");
        } else if (fragment == null) {
            // only create fragment if they haven't been instantiated already
            fragment=  DefinitionListFragment.getInstance();
        }
        showFragment(fragment, "DefinitionListFragment");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setQuery(mSearchQuery, false);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.refine_search:
                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // capture text query from serachview entry or voice
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mSearchQuery = String.valueOf(query);
            mSearchView.setQuery(mSearchQuery, false);
            Bus.fetchDefinitionsEvent(query);
        }
    }

    private void showFragment(final Fragment fragment, final String fragmentName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, fragmentName)
                .commitAllowingStateLoss();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final String itemTitle = (String) item.getTitle();
        Bus.sortDefinitionsEvent(itemTitle.equals(getString(R.string.thumbs_down_sort)) ?
                DefinitionComparatorType.THUMBS_DOWN : DefinitionComparatorType.THUMBS_UP);
        mDrawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
}
