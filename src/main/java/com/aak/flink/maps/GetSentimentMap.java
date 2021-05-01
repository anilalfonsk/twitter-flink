package com.aak.flink.maps;

import com.aak.flink.constants.Sentiment;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

import java.util.Properties;

public class GetSentimentMap extends RichMapFunction<String, Tuple2<String, Sentiment>> {

    private StanfordCoreNLP pipeline;

    @Override
    public void open(Configuration parameters){
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    @Override
    public Tuple2<String, Sentiment> map(String value) throws Exception {
        int sentiment = this.analyse(value);
        Sentiment sentimentEnum = Sentiment.getSentiment(sentiment);
        return Tuple2.of(value, sentimentEnum);
    }

    public int analyse(String tweet) {
        Annotation annotation = pipeline.process(tweet);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            return RNNCoreAnnotations.getPredictedClass(tree);
        }
        return 0;
    }
}
