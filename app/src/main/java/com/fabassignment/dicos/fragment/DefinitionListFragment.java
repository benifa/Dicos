package com.fabassignment.dicos.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fabassignment.dicos.R;
import com.fabassignment.dicos.adapter.DefinitionListAdapter;
import com.fabassignment.dicos.controller.retrofit.UrbanDictionaryController;
import com.fabassignment.dicos.enums.DefinitionComparatorType;
import com.fabassignment.dicos.model.dictionary.Definition;
import com.fabassignment.dicos.model.dictionary.DefinitionContainer;
import com.fabassignment.dicos.model.event.DefinitionSortEvent;
import com.fabassignment.dicos.model.event.FetchDefinitionsEvent;
import com.fabassignment.dicos.model.event.TermDefinitionsFetchedEvent;
import com.fabassignment.dicos.model.response.BaseResponse;
import com.joanzapata.iconify.widget.IconTextView;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by benifabrice on 5/14/17.
 */

public class DefinitionListFragment extends BusListenerFragment implements DefinitionsView{
    @BindView(R.id.definition_list)
    protected RecyclerView mDefinitionListRecyclerView;
    @BindView(R.id.loading_view)
    protected ContentLoadingProgressBar mProgressbar;
    @BindView(R.id.no_results)
    protected IconTextView noResultsView;
    private Unbinder mUnbinder;
    private DefinitionListAdapter definitionListAdapter;
    private List<Definition> originalDefinitionList;
    private List<Definition> thumbsDownDefinitionList;
    private List<Definition> thumbsUpDefinitionList;

    public static DefinitionListFragment getInstance() {
        return new DefinitionListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            originalDefinitionList = (List<Definition>) savedInstanceState.getSerializable("original");
            setLists();
        }

        DefinitionListPresenter presenter = new DefinitionListPresenter(this, null);
        return inflater.inflate(R.layout.fragment_definitions_list, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("original", (Serializable) originalDefinitionList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mDefinitionListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDefinitionList(originalDefinitionList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Subscribe
    public void onEvent(FetchDefinitionsEvent event) {
        mDefinitionListRecyclerView.setVisibility(View.GONE);
        mProgressbar.show();
        new UrbanDictionaryController().getDefinitions(event.getSearchTerm());
    }

    @Subscribe
    public void onEvent(TermDefinitionsFetchedEvent event) {
        mProgressbar.hide();
        if (event.isSuccess()) {

            final BaseResponse response = event.getResponseBoody();
            if (response instanceof DefinitionContainer) {
                originalDefinitionList = ((DefinitionContainer) response).getList();

                setLists();

                if (originalDefinitionList != null && !originalDefinitionList.isEmpty()) {
                    mDefinitionListRecyclerView.setVisibility(View.VISIBLE);
                    noResultsView.setVisibility(View.GONE);
                    updateDefinitionList(originalDefinitionList);

                } else {
                    mDefinitionListRecyclerView.setVisibility(View.GONE);
                    noResultsView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            mDefinitionListRecyclerView.setVisibility(View.GONE);
            noResultsView.setVisibility(View.VISIBLE);
        }
    }

    private void setLists() {
        if (originalDefinitionList ==null) {
            return;
        }
        if (thumbsDownDefinitionList == null) {
            thumbsDownDefinitionList = new ArrayList<>(originalDefinitionList);
        } else {
            thumbsDownDefinitionList.clear();
            thumbsDownDefinitionList.addAll(originalDefinitionList);
        }

        Collections.sort(thumbsDownDefinitionList, Definition.ThumbsDownComparator);

        if (thumbsUpDefinitionList == null) {
            thumbsUpDefinitionList = new ArrayList<>(originalDefinitionList);
        } else {
            thumbsUpDefinitionList.clear();
            thumbsUpDefinitionList.addAll(originalDefinitionList);
        }

        Collections.sort(thumbsUpDefinitionList, Definition.ThumbsUpComparator);
    }

    private void updateDefinitionList(List<Definition> definitionList) {
        mProgressbar.hide();
        if (definitionList == null || definitionList.isEmpty()) {
            return;
        }
        if (definitionListAdapter == null) {
            definitionListAdapter = new DefinitionListAdapter(definitionList);
            mDefinitionListRecyclerView.setAdapter(definitionListAdapter);
        }
        definitionListAdapter.setDefinitionList(definitionList);
        definitionListAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEvent(DefinitionSortEvent event) {
        switch (event.getSortType()) {
            case DefinitionComparatorType.THUMBS_DOWN:
                updateDefinitionList(thumbsDownDefinitionList);
                break;
            case DefinitionComparatorType.THUMBS_UP:
                updateDefinitionList(thumbsUpDefinitionList);
                break;
            default:
                updateDefinitionList(originalDefinitionList);

        }

    }


    @Override
    public void displayDefinitions(List<Definition> definitions) {

    }

    @Override
    public void displayNoDefinitions() {

    }
}
