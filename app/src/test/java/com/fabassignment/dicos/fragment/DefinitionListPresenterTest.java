package com.fabassignment.dicos.fragment;

import android.text.TextUtils;

import com.fabassignment.dicos.model.dictionary.Definition;
import com.fabassignment.dicos.repositories.DefinitionsRepository;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by fabricebenimana on 4/12/18.
 */
public class DefinitionListPresenterTest {


    @Test
    public void shouldPassDefinitionsToView() {
        //given
        DefinitionsView view = new MockView();
        MockDefinitionsRepository repository = new MockDefinitionsRepository();

        // when
        DefinitionListPresenter presenter = new DefinitionListPresenter(view, repository);
        presenter.fetchDefinitions("fabrice");

        //then
        Assert.assertEquals(true, ((MockView)view).booksFound);
    }

    @Test
    public void shouldHandleNoDefinitionsFound(){
        //given
        DefinitionsView view = new MockView();
        MockDefinitionsRepository repository = new MockDefinitionsRepository();

        // when
        DefinitionListPresenter presenter = new DefinitionListPresenter(view, repository);
        presenter.fetchDefinitions(null);

        Assert.assertEquals(true, ((MockView)view).booksNoFound);


    }

    private class MockView implements DefinitionsView{

        boolean booksFound;
        boolean booksNoFound;

        @Override
        public void displayDefinitions(List<Definition> definitions) {
            booksFound = definitions.size() == 3;
        }

        @Override
        public void displayNoDefinitions() {
            booksNoFound = true;

        }
    }

    private class MockDefinitionsRepository implements DefinitionsRepository{

        @Override
        public List<Definition> getDefinitions(String query) {

            return  query != null  ? Arrays.asList(new Definition(), new Definition(), new Definition())
                    : Collections.<Definition>emptyList();

        }
    }



}