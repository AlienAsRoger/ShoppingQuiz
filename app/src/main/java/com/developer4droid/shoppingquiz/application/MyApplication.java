package com.developer4droid.shoppingquiz.application;

import android.app.Application;
import com.developer4droid.shoppingquiz.inject.AppModule;
import com.developer4droid.shoppingquiz.inject.components.DaggerGlobalComponent;
import com.developer4droid.shoppingquiz.inject.components.GlobalComponent;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:47
 */

public class MyApplication extends Application {

	private static MyApplication instance;
	private GlobalComponent component;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		component = DaggerGlobalComponent.builder()
				.appModule(new AppModule(this))
				.build();
	}

	public static MyApplication getInstance() {
		return instance;
	}

	public GlobalComponent getGlobalComponent() {
		return component;
	}
}
