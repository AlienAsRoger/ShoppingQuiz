package com.developer4droid.shoppingquiz.inject;

import com.developer4droid.shoppingquiz.network.DataLoader;
import com.developer4droid.shoppingquiz.network.DataLoaderImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:48
 */

@Module
public class NetworkModule {

	@Provides
	DataLoader provideDataLoader() {
		return new DataLoaderImpl();
	}
}
