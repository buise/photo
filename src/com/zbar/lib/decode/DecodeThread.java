package com.zbar.lib.decode;

import java.util.concurrent.CountDownLatch;

import com.zbar.lib.CaptureActivity;

import android.os.Handler;
import android.os.Looper;

final class DecodeThread extends Thread {

	public static final String BARCODE_BITMAP = "barcode_bitmap";
	
	CaptureActivity activity;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	DecodeThread(CaptureActivity activity) {
		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
