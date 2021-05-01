package com.aak.flink.filters;

import com.google.gson.JsonObject;
import org.apache.flink.api.common.functions.FilterFunction;

public class LanguageFilter implements FilterFunction<JsonObject> {
    @Override
    public boolean filter(JsonObject value) throws Exception {
        return value.get("lang").getAsString().equalsIgnoreCase("en");
    }
}
