package com.aak.flink.maps;

import com.aak.flink.StreamingJob;
import com.aak.flink.models.ErrorModel;
import com.aak.flink.models.TweetsInfo;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class MapToModel extends ProcessFunction<JsonObject, TweetsInfo> {

    @Override
    public void processElement(JsonObject value, Context ctx, Collector<TweetsInfo> out) {
        try {
            String userId = value.get("id_str").getAsString();
            String fullTweetText = value.get("text").getAsString();
            if (fullTweetText.startsWith("RT")) {
                fullTweetText = value.get("retweeted_status").getAsJsonObject()
                        .get("extended_tweet").getAsJsonObject()
                        .get("full_text").getAsString();
            } else if (value.get("truncated").getAsBoolean()) {
                fullTweetText = value.get("extended_tweet").getAsJsonObject()
                        .get("full_text").getAsString();
            }
            out.collect(TweetsInfo.builder().rawTweet(fullTweetText).userId(userId).keyWord("Flipkart").build());
        }catch (Exception e){
            ErrorModel errorModel = new ErrorModel();
            errorModel.setTweet(value != null ? value.toString() : "N/A");
            errorModel.setException(ExceptionUtils.getStackTrace(e));
            ctx.output(StreamingJob.outputTag, errorModel);
        }
    }
}
