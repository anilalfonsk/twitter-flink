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
import com.aak.flink.maps.MapToJson;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import java.util.Properties;

public class StreamingJob {

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		Properties props = new Properties();
		props.setProperty(TwitterSource.CONSUMER_KEY, "");
		props.setProperty(TwitterSource.CONSUMER_SECRET, "");
		props.setProperty(TwitterSource.TOKEN, "");
		props.setProperty(TwitterSource.TOKEN_SECRET, "");
		TwitterSource tws = new TwitterSource(props);
		tws.setCustomEndpointInitializer(new SearchEndPoints());
		DataStream<String> streamSource = env.addSource(tws);
		DataStream<JsonObject> jsonStream = streamSource
				.filter(new EmptyFilter())
				.flatMap(new MapToJson());
		DataStream<JsonObject> filteredStream = jsonStream.filter(new LanguageFilter());
		filteredStream.print();
		env.execute("Flink Streaming Java API Skeleton");
	}
}
