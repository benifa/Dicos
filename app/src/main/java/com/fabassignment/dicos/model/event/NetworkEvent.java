package com.fabassignment.dicos.model.event;

import com.fabassignment.dicos.model.response.BaseResponse;

/**
 * Created by benifabrice on 5/13/17.
 */

public class NetworkEvent<T extends BaseResponse> extends BaseEvent {
    private boolean isSuccess;
    private T responseBoody;


    public NetworkEvent(final T responseBody, boolean isSuccess) {
        this.responseBoody = responseBody;
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getResponseBoody() {
        return responseBoody;
    }
}
