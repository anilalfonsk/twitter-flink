package com.aak.flink.patterns;

import com.aak.flink.constants.Sentiment;
import com.aak.flink.models.TweetsInfo;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentimentPatterns {

    private static final Logger logger = LoggerFactory.getLogger(SentimentPatterns.class);

    public static Pattern<TweetsInfo, ?> negativeSentiment = Pattern.<TweetsInfo>begin("firstEvent")
            .where(new IterativeCondition<TweetsInfo>() {
                @Override
                public boolean filter(TweetsInfo value, Context<TweetsInfo> ctx) throws Exception {
                    logger.info("Sentiment {}",Sentiment.isSentimentNegative(value.getSentiment()));
                    return Sentiment.isSentimentNegative(value.getSentiment());
                }
            }).times(4);
}
