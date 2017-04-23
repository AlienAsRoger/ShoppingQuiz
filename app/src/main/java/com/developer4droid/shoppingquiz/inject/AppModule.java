package com.developer4droid.shoppingquiz.inject;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:47
 */

@Module
public class AppModule {
	Application application;

	public AppModule(Application application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Application providesApplication() {
		return application;
	}

	@Provides
	@Singleton
	EventBus provideEventBus() {
		return EventBus.getDefault();
	}

}
