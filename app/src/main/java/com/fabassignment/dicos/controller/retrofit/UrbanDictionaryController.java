package com.fabassignment.dicos.controller.retrofit;

import com.fabassignment.dicos.controller.Bus;
import com.fabassignment.dicos.model.dictionary.DefinitionContainer;
import com.fabassignment.dicos.retrofit.ApiClient;
import com.fabassignment.dicos.retrofit.UrbanDictionaryService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by benifabrice on 5/13/17.
 */

public class UrbanDictionaryController {
    private static final String URBAN_DICTIONARY_BASE_URL = "https://mashape-community-urban-dictionary.p.mashape.com";

    private UrbanDictionaryService dictionaryService;

    public UrbanDictionaryController() {
        dictionaryService = ApiClient.getInstance(URBAN_DICTIONARY_BASE_URL).create(UrbanDictionaryService.class);
    }

    public void getDefinitions(final String searchTerm) {
        dictionaryService.getDefinitions(searchTerm).enqueue(new Callback<DefinitionContainer>() {
            @Override
            public void onResponse(Call<DefinitionContainer> call, Response<DefinitionContainer> response) {
                Bus.onDefinitionsFetchedEvent(response.body(), response.isSuccessful());
            }

            @Override
            public void onFailure(Call<DefinitionContainer> call, Throwable t) {

            }
        });
    }
}
