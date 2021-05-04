/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aak.flink;

import com.aak.flink.endpoints.SearchEndPoints;
import com.aak.flink.filters.EmptyFilter;
import com.aak.flink.filters.LanguageFilter;
import com.aak.flink.maps.GetCleanedTweetTextMap;
import com.aak.flink.maps.GetSentimentMap;
import com.aak.flink.maps.MapToJson;
import com.aak.flink.maps.MapToModel;
import com.aak.flink.models.ErrorModel;
import com.aak.flink.models.TweetsInfo;
import com.aak.flink.patterns.SentimentPatterns;
import com.aak.flink.patterns.TweetNegativePatternProcessFunction;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.util.OutputTag;

import java.util.List;
import java.util.Properties;

import static org.apache.flink.cep.CEP.pattern;

public class StreamingJob {

	public static final OutputTag<ErrorModel> outputTag = new OutputTag<ErrorModel>("errorOutput"){};

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		Properties props = new Properties();
		props.setProperty(TwitterSource.CONSUMER_KEY, "");
		props.setProperty(TwitterSource.CONSUMER_SECRET, "");
		props.setProperty(TwitterSource.TOKEN, "");
		props.setProperty(TwitterSource.TOKEN_SECRET, "");
		TwitterSource tws = new TwitterSource(props);
		tws.setCustomEndpointInitializer(new SearchEndPoints());
		DataStream<String> streamSource = env.addSource(tws)
			.assignTimestampsAndWatermarks(new IngestionTimeExtractor<>());
		SingleOutputStreamOperator<TweetsInfo> modelStream = streamSource
				.filter(new EmptyFilter())
				.flatMap(new MapToJson())
				.filter(new LanguageFilter())
				.process(new MapToModel());
//		modelStream.getSideOutput(StreamingJob.outputTag)
//				.print();

		DataStream<TweetsInfo> tweetWithSentimentStream = modelStream
				.map(new GetCleanedTweetTextMap())
				.map(new GetSentimentMap());

//		tweetWithSentimentStream.print();

		//Analyse for continues negative sentiments
		PatternStream<TweetsInfo> tweetsInfoPatternStream = CEP.pattern(tweetWithSentimentStream
				.keyBy((KeySelector<TweetsInfo, String>) value -> value.getKeyWord()), SentimentPatterns.negativeSentiment);
		DataStream<TweetsInfo> tweetsInfoDataStream = tweetsInfoPatternStream
				.process(new TweetNegativePatternProcessFunction());
		tweetsInfoDataStream.print();



		env.execute("Flink Streaming Java API Skeleton");
	}
}
