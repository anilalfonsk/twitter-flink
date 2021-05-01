package com.aak.flink;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Scratch {
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("N6JDR2son3kKvcgEK64rqqkff")
                .setOAuthConsumerSecret("VPd1x7LNi6DKdOQt1b6kHUHdmDwm2sffojoNSOjxOuxOErZ5dC")
                .setOAuthAccessToken("1860582534-nfczpX0bHuXbkksREjEF857VVxAEbNEdvwJ5uPh")
                .setOAuthAccessTokenSecret("VCR4Y2JfsWHLiTB0nuZjOVJ2t04fUfIS7KOwqssl2RcMq");
//        TwitterFactory tf = new TwitterFactory(cb.build());
//        Twitter twitter = tf.getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }
            @Override
            public void onDeletionNotice(StatusDeletionNotice arg) {
            }
            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            }
            @Override
            public void onStallWarning(StallWarning warning) {
            }
            @Override
            public void onStatus(Status status) {
                System.out.println(status);
            }
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }
        };

        TwitterStreamFactory tsf = new TwitterStreamFactory(cb.build());
        TwitterStream twitterStream = tsf.getInstance();
        Query query = new Query();
        query.setLang("en");
        twitterStream.addListener(listener);
        twitterStream.filter(new FilterQuery());

        twitterStream.sample("en");
    }
}
