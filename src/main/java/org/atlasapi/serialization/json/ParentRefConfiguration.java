package org.atlasapi.serialization.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 */
public abstract class  ParentRefConfiguration {
    
    @JsonCreator
    ParentRefConfiguration(@JsonProperty("uri") String uri) {
    }
}
