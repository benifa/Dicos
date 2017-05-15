package com.fabassignment.dicos.model.event;

import com.fabassignment.dicos.model.response.BaseResponse;

/**
 * Created by benifabrice on 5/13/17.
 */

public class TermDefinitionsFetchedEvent<DefinitionContainer> extends NetworkEvent {

    public TermDefinitionsFetchedEvent(final DefinitionContainer definitionContainer,
                                       boolean isSucess) {
        super((BaseResponse) definitionContainer, isSucess);
    }

}
