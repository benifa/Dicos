package com.fabassignment.dicos.model.event;

/**
 * Created by benifabrice on 5/13/17.
 */

public class FetchDefinitionsEvent extends BaseEvent {
    private String searchTerm;
    public FetchDefinitionsEvent(final String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
}
