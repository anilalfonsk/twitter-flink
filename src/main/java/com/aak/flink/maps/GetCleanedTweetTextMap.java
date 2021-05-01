package com.aak.flink.maps;

import com.google.gson.JsonObject;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class GetCleanedTweetTextMap implements FlatMapFunction<JsonObject, String> {
    @Override
    public void flatMap(JsonObject value, Collector<String> out) throws Exception {
        String fullTweetText = value.get("text").getAsString();
        if(fullTweetText.startsWith("RT")){
            fullTweetText = value.get("retweeted_status").getAsJsonObject()
                    .get("extended_tweet").getAsJsonObject()
                    .get("full_text").getAsString();
        }else if(value.get("truncated").getAsBoolean()){
            fullTweetText = value.get("extended_tweet").getAsJsonObject()
                    .get("full_text").getAsString();
        }
        String cleanedTweetText = fullTweetText.trim()
                // remove links
                .replaceAll("http.*?[\\S]+", "")
                // remove usernames
                .replaceAll("@[\\S]+", "")
                // replace hashtags by just words
                .replaceAll("#", "")
                // correct all multiple white spaces to a single white space
                .replaceAll("[\\s]+", " ");
        out.collect(cleanedTweetText);
    }
}
