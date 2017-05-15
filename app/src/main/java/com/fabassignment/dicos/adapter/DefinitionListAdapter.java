package com.fabassignment.dicos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fabassignment.dicos.R;
import com.fabassignment.dicos.model.dictionary.Definition;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by benifabrice on 5/14/17.
 */

public class DefinitionListAdapter extends RecyclerView.Adapter<DefinitionListAdapter.ViewHolder> {
    private List<Definition> mDefinitionList;

    public DefinitionListAdapter(final List<Definition> definitions) {
        mDefinitionList = definitions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Definition definition = mDefinitionList.get(position);
        holder.mDefinition.setText(definition.getDefinition());
        holder.mAuthor.setText(String.format(holder.mAuthorFormat,definition.getAuthor()));
        holder.mThumbsDown.setText(String.format(holder.mThumbsDownFormat, definition.getThumbsDown()));
        holder.mThumbsUp.setText(String.format(holder.mThumbsUpFormat, definition.getThumbsUp()));
    }

    public void setDefinitionList(List<Definition> mDefinitionList) {
        this.mDefinitionList = mDefinitionList;
    }

    @Override
    public int getItemCount() {
        return mDefinitionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.definition)
        protected TextView mDefinition;
        @BindView(R.id.author)
        protected TextView mAuthor;
        @BindView(R.id.thumbs_down)
        protected IconTextView mThumbsDown;
        @BindView(R.id.thumbs_up)
        protected IconTextView mThumbsUp;
        @BindString(R.string.thumbs_up_format)
        protected String mThumbsUpFormat;
        @BindString(R.string.thumbs_down_format)
        protected String mThumbsDownFormat;
        @BindString(R.string.author_format)
        protected String mAuthorFormat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
