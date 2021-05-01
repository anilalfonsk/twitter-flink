package com.aak.flink.endpoints;

import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import com.twitter.hbc.core.endpoint.Location;
import java.io.Serializable;
import java.util.List;

public class FilterEndPoints implements TwitterSource.EndpointInitializer, Serializable {
    private static List<String> wordsToFilter;
    private static List<Location> locations;

    public FilterEndPoints(List<String> wordsToFilter, List<Location> locations){
        this.wordsToFilter = wordsToFilter;
        this.locations = locations;
    }

    public StreamingEndpoint createEndpoint(){
        StatusesFilterEndpoint statusesFilterEndpoint = new StatusesFilterEndpoint();
        if(wordsToFilter != null) statusesFilterEndpoint.trackTerms(wordsToFilter);
        if(locations != null) statusesFilterEndpoint.locations(locations);
        return statusesFilterEndpoint;
    }

}
