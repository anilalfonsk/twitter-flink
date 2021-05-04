package com.aak.flink.maps;

import com.aak.flink.models.ErrorModel;
import com.aak.flink.models.TweetsInfo;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.util.Collector;

public class GetCleanedTweetTextMap extends RichMapFunction<TweetsInfo, TweetsInfo> {
    @Override
    public TweetsInfo map(TweetsInfo value) throws Exception {
        String fullTweetText = value.getRawTweet();
        String cleanedTweetText = fullTweetText.trim()
                // remove links
                .replaceAll("http.*?[\\S]+", "")
                // remove usernames
                .replaceAll("@[\\S]+", "")
                // replace hashtags by just words
                .replaceAll("#", "")
                // correct all multiple white spaces to a single white space
                .replaceAll("[\\s]+", " ");
        value.setCleanedTweet(cleanedTweetText);
        return value;
    }

}
