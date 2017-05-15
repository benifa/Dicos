package com.fabassignment.dicos.model.dictionary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by benifabrice on 5/14/17.
 */
public class DefinitionTest {
    private static final String TEST_STR = "test";
    private static final int TEST_INT = 0;
    private Definition definitionOne;
    private Definition definitionTwo;
    private Definition definitionThree;

    @Before
    public void setUp() {
        definitionOne = buildDefinition(1,0);
        definitionTwo = buildDefinition(0,1);
        definitionThree = buildDefinition(0,1);
    }

    private Definition buildDefinition(final int thumbsUp, final int thumbsDown) {
        final Definition definition = new Definition();
        definition.setAuthor(TEST_STR);
        definition.setThumbsDown(thumbsDown);
        definition.setThumbsUp(thumbsUp);
        definition.setDefinition(TEST_STR);
        definition.setDefid(TEST_INT);
        definition.setPermalink(TEST_STR);
        definition.setWord(TEST_STR);
        return definition;
    }

    @Test
    public void testEqual() {
        int result = Definition.ThumbsDownComparator.compare(definitionTwo, definitionThree);
        assertTrue("expected to be equal", result == 0);
        result = Definition.ThumbsUpComparator.compare(definitionTwo, definitionThree);
        assertTrue("expected to be equal", result == 0);
    }

    @Test
    public void testGreaterThan() {
        int result = Definition.ThumbsDownComparator.compare(definitionOne, definitionTwo);
        assertTrue("expected to be greater than", result >= 1);
        result = Definition.ThumbsUpComparator.compare(definitionTwo, definitionOne);
        assertTrue("expected to be greater than", result >= 1);
    }


    @Test
    public void testLessThan() {
        int result = Definition.ThumbsDownComparator.compare(definitionTwo, definitionOne);
        assertTrue("expected to be less than", result <= -1);
        result = Definition.ThumbsUpComparator.compare(definitionOne, definitionTwo);
        assertTrue("expected to be less than", result <= -1);
    }



}