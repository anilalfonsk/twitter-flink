package com.aak.flink.maps;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class MapToJson implements FlatMapFunction<String, JsonObject> {

    @Override
    public void flatMap(String value, Collector<JsonObject> out) throws Exception {
        JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
        out.collect(jsonObject);
    }
}
