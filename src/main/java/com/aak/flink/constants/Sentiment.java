package com.aak.flink.constants;

public enum Sentiment {
    VERY_NEGATIVE(0),
    NEGATIVE(1),
    NEUTRAL(2),
    POSITIVE(3),
    VERY_POSITIVE(4),
    NOT_DEFINED(999);

    private Integer value;

    Sentiment(Integer value) {
        this.value = value;
    }

    public static Sentiment getSentiment(int i){
        switch (i){
            case 0: return VERY_NEGATIVE;
            case 1: return NEGATIVE;
            case 2: return NEUTRAL;
            case 3: return POSITIVE;
            case 4: return VERY_POSITIVE;
            default: System.out.println("No SENTIMENT");
        }
        return NOT_DEFINED;
    }
}
