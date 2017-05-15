package com.fabassignment.dicos.model.dictionary;

import com.fabassignment.dicos.model.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by benifabrice on 5/13/17.
 */

public class DefinitionContainer extends BaseResponse{
    private List<String> tags;
    @SerializedName("result_type")
    private String resultType;
    private List<Definition> list;

    public List<Definition> getList() {
        return list;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getResultType() {
        return resultType;
    }
}
