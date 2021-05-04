package com.aak.flink.maps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapToJson extends RichFlatMapFunction<String, JsonObject> {

    Logger logger = LoggerFactory.getLogger(MapToJson.class);

    @Override
    public void flatMap(String value, Collector<JsonObject> out) throws Exception {
        JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
        out.collect(jsonObject);
    }
}
