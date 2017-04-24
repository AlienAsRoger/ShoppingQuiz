package com.developer4droid.shoppingquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:52
 */

public class QuizItem implements Parcelable {
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

	protected QuizItem(Parcel in) {
		name = in.readString();
		if (in.readByte() == 0x01) {
			urls = new ArrayList<String>();
			in.readList(urls, String.class.getClassLoader());
		} else {
			urls = null;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		if (urls == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(urls);
		}
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<QuizItem> CREATOR = new Parcelable.Creator<QuizItem>() {
		@Override
		public QuizItem createFromParcel(Parcel in) {
			return new QuizItem(in);
		}

		@Override
		public QuizItem[] newArray(int size) {
			return new QuizItem[size];
		}
	};
}