package com.fabassignment.dicos.model.event;

import com.fabassignment.dicos.enums.DefinitionComparatorType;

/**
 * Created by benifabrice on 5/14/17.
 */

public class DefinitionSortEvent extends BaseEvent {
    private @DefinitionComparatorType.Interface int mSortType;
    public DefinitionSortEvent (@DefinitionComparatorType.Interface int sortType){
        this.mSortType = sortType;
    }

    public @DefinitionComparatorType.Interface int getSortType() {
        return mSortType;
    }
}
