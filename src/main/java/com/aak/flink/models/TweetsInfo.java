package com.aak.flink.models;

import com.aak.flink.constants.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TweetsInfo {
    private String keyWord;
    private String userId;
    private String rawTweet;
    private String cleanedTweet;
    private Sentiment sentiment;
}
