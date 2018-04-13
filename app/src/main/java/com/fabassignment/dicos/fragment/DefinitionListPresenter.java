package com.fabassignment.dicos.fragment;

import com.fabassignment.dicos.controller.Bus;
import com.fabassignment.dicos.model.dictionary.Definition;
import com.fabassignment.dicos.repositories.DefinitionsRepository;

import java.util.Collections;
import java.util.List;

/**
 * Created by fabricebenimana on 4/12/18.
 */

class DefinitionListPresenter {
    private DefinitionsView mDefinitionsView;
    private DefinitionsRepository mDefinitionsRepository;

    public DefinitionListPresenter(DefinitionsView definitionsView, DefinitionsRepository definitionsRepository) {
        mDefinitionsView = definitionsView;
        mDefinitionsRepository = definitionsRepository;
    }

    public void fetchDefinitions(String query) {
        List<Definition> definitions = mDefinitionsRepository.getDefinitions(query);
        if (definitions != null && definitions.size() > 0)
            mDefinitionsView.displayDefinitions(definitions);
        else
            mDefinitionsView.displayNoDefinitions();
    }
}
