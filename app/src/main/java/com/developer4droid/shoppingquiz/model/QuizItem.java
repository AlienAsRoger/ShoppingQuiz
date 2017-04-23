package com.developer4droid.shoppingquiz.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:52
 */

public class QuizItem {
	/*
      "name": "365 Organic Frosted Flakes Cereal",
      "urls": [
        "https://d2lnr5mha7bycj.cloudfront.net/itemimage/image/12215-eaac44d114cb00be4e3ecc26e0cf39be.jpg",
        "https://d2lnr5mha7bycj.cloudfront.net/itemimage/image/12217-9186e76a368e196da3bc4b655d02ca30.jpg",
        "https://d2lnr5mha7bycj.cloudfront.net/itemimage/image/12212-0e2a15ef92d3a7b48ef53c4162b3c836.jpg",
        "https://d2lnr5mha7bycj.cloudfront.net/itemimage/image/12189-97c5e14e27d6344a34a9c9d903e0dc5a.jpg"
      ]
	 */

	private String name;
	private List<String> urls;

	public String getName() {
		return name;
	}

	public List<String> getUrls() {
		return urls;
	}
}
