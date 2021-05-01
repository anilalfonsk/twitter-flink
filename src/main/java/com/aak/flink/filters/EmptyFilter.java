package com.aak.flink.filters;

import org.apache.flink.api.common.functions.FilterFunction;

public class EmptyFilter implements FilterFunction<String> {
    @Override
    public boolean filter(String value) throws Exception {
        return value != null && value.length() > 0;
    }
}
