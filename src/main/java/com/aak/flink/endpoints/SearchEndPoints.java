package com.aak.flink.endpoints;

import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.io.Serializable;
import java.util.Arrays;

public class SearchEndPoints implements TwitterSource.EndpointInitializer, Serializable {
    @Override
    public StreamingEndpoint createEndpoint() {
        StatusesFilterEndpoint statusesFilterEndpoint = new StatusesFilterEndpoint();
        statusesFilterEndpoint.trackTerms(Arrays.asList("@Flipkart"));
        statusesFilterEndpoint.stallWarnings(false);
        statusesFilterEndpoint.delimited(false);
        return statusesFilterEndpoint;
    }
}
