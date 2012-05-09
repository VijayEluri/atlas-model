package org.atlasapi.serialization.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.metabroadcast.common.intl.Country;

/**
 */
public interface CountryConfiguration {
    
    @JsonCreator
    Country make(@JsonProperty("code") String code, @JsonProperty("name") String name);
}
