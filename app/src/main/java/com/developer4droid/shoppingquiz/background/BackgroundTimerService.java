package com.developer4droid.shoppingquiz.background;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.QuizCompleteEvent;
import com.developer4droid.shoppingquiz.events.TimerFinishedEvent;
import com.developer4droid.shoppingquiz.events.TimerUpdateEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

public class BackgroundTimerService extends Service {

	private static final long QUIZ_TIME = 2 * 60 * 1000; // 2 min
//	private static final long QUIZ_TIME = 5 * 1000; // test time
	private static final long PERIOD = 1000; // 1 sec

	@Inject
	EventBus eventBus;

	private CountDownTimer countDownTimer;
	private int startId;

	public BackgroundTimerService() {
		MyApplication.getInstance().getGlobalComponent().inject(this);
	}

	public class ServiceBinder extends Binder {
		public BackgroundTimerService getService() {
			return BackgroundTimerService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, final int startId) {
		this.startId = startId;
		eventBus.unregister(this); // unregister first

		eventBus.register(this);
		countDownTimer = new CountDownTimer(QUIZ_TIME, PERIOD) {

			public void onTick(long millisUntilFinished) {
				eventBus.post(new TimerUpdateEvent(millisUntilFinished));
			}

			public void onFinish() {
				eventBus.post(new TimerFinishedEvent());
				stopSelf(startId);
			}
		}.start();
		return START_STICKY_COMPATIBILITY;
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(QuizCompleteEvent event) {
		countDownTimer.cancel();

		eventBus.unregister(this);
		stopSelf(startId);
	}

}
