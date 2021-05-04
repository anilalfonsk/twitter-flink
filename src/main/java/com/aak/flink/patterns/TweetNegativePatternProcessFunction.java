package com.aak.flink.patterns;

import com.aak.flink.models.TweetsInfo;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;

public class TweetNegativePatternProcessFunction extends PatternProcessFunction<TweetsInfo, TweetsInfo> {
    @Override
    public void processMatch(Map<String, List<TweetsInfo>> match, Context ctx, Collector<TweetsInfo> out) throws Exception {
        out.collect(match.get("firstEvent").get(0));
    }
}
