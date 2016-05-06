package com.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TwitterRestController {

	@Value("${consumerKey}")
	private String consumerKey;

	@Value("${consumerSecret}")
	private String consumerSecret;

	@Value("${accessToken}")
	private String accessToken;

	@Value("${accessTokenSecret}")
	private String accessTokenSecret;

	@RequestMapping("/username")
	public @ResponseBody List<com.test.Tweet> searchForUser(@RequestParam(value = "name") String name) {
		return searchTwitterWithSpringTemplate(name, 20);
	}

	@RequestMapping("/usernameAndCount")
	public @ResponseBody List<com.test.Tweet> searchForUserAndCount(@RequestParam(value = "name") String name,
			@RequestParam(value = "count") int count) {
		return searchTwitterWithSpringTemplate(name, count);
	}

	private List<com.test.Tweet> searchTwitterWithSpringTemplate(String name, int count) {
		TwitterTemplate twitterTemplate = new TwitterTemplate(consumerKey, consumerSecret, accessToken,
				accessTokenSecret);
		TimelineOperations timelineOperations = twitterTemplate.timelineOperations();
		List<Tweet> userTimeTweets = timelineOperations.getUserTimeline(name, count);
		List<com.test.Tweet> tweets = new ArrayList<com.test.Tweet>();
		for (Tweet userTimeTweet : userTimeTweets) {
			com.test.Tweet tweet = new com.test.Tweet();
			tweet.setText(userTimeTweet.getText());
			tweets.add(tweet);
		}
		return tweets;
	}
}
