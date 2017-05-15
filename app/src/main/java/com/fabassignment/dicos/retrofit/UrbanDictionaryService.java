package com.fabassignment.dicos.retrofit;

import com.fabassignment.dicos.model.dictionary.DefinitionContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by benifabrice on 5/13/17.
 */

public interface UrbanDictionaryService {

    @GET("/define")
    @Headers({"Accept: text/plain","X-Mashape-Key: ssMqVEtfKomshDuXjCYmXnzytKkfp1sowlPjsncWBbXbkDZhhK"})
    Call<DefinitionContainer> getDefinitions(@Query("term") String searchTerm);
}
