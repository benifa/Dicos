package com.fabassignment.dicos.controller;

import com.fabassignment.dicos.enums.DefinitionComparatorType;
import com.fabassignment.dicos.model.dictionary.DefinitionContainer;
import com.fabassignment.dicos.model.event.BaseEvent;
import com.fabassignment.dicos.model.event.DefinitionSortEvent;
import com.fabassignment.dicos.model.event.FetchDefinitionsEvent;
import com.fabassignment.dicos.model.event.TermDefinitionsFetchedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by benifabrice on 5/13/17.
 */

public class Bus {
    private static final int MAX_RANDOM_INT = 15;

    private Bus() {}

    public static void register(Object object) {
        EventBus.getDefault().register(object);
    }

    public static void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }

    private static void post(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    private static void postSticky(BaseEvent event) {
        EventBus.getDefault().postSticky(event);
    }

    private static BaseEvent getStickyEvent(final Class clazz) {
        final Object event = EventBus.getDefault().getStickyEvent(clazz);
        if (event != null && event instanceof BaseEvent) {
            return (BaseEvent) event;
        }
        return null;
    }

    public static boolean isStickyEventPosted(final Class clazz) {
        return getStickyEvent(clazz) != null;
    }

    public static void removeStickyEvent(final Class clazz) {
        final BaseEvent event = getStickyEvent(clazz);
        if (event != null) {
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    public static void onDefinitionsFetchedEvent(DefinitionContainer definitionContainer, boolean isSuccess) {
        post(new TermDefinitionsFetchedEvent(definitionContainer, isSuccess));
    }

    public static void fetchDefinitionsEvent(final String searchTerm) {
        post(new FetchDefinitionsEvent(searchTerm));
    }

    public static void sortDefinitionsEvent(@DefinitionComparatorType.Interface int sortType) {
        post(new DefinitionSortEvent(sortType));
    }
}
