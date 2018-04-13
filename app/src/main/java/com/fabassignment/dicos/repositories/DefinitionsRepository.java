package com.fabassignment.dicos.repositories;

import com.fabassignment.dicos.model.dictionary.Definition;

import java.util.List;

/**
 * Created by fabricebenimana on 4/12/18.
 */

public interface DefinitionsRepository {

    List<Definition> getDefinitions(String query);
}
