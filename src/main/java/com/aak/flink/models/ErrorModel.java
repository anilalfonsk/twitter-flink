package com.aak.flink.models;

import lombok.Data;

@Data
public class ErrorModel {
    private String exception;
    private String tweet;
}
