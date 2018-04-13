package com.fabassignment.dicos.fragment;

import com.fabassignment.dicos.model.dictionary.Definition;

import java.util.List;

/**
 * Created by fabricebenimana on 4/12/18.
 */

interface DefinitionsView {

    void displayDefinitions(List<Definition> definitions);
    void displayNoDefinitions();
}
