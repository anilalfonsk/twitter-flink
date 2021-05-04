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

    public static boolean isSentimentNegative(Sentiment sentiment){
        if(sentiment == NEGATIVE || sentiment == VERY_NEGATIVE) return true;
        return false;
    }

    public static boolean isSentimentPositive(Sentiment sentiment){
        if(sentiment == POSITIVE || sentiment == VERY_POSITIVE) return  true;
        return false;
    }

    public static boolean isSentimentNeutral(Sentiment sentiment){
        if(sentiment == NEUTRAL) return true;
        return false;
    }
}
