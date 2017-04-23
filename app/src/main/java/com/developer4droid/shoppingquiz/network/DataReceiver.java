package com.developer4droid.shoppingquiz.network;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:56
 */

public interface DataReceiver<T> {

	void onDataReceived(T data);
}
