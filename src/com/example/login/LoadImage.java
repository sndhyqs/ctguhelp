package com.example.login;

import java.io.File;

import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.DragImageView;
import com.ctgu.util.AppCache;

import cn.itcast.net.download.DownloadProgressListener;
import cn.itcast.net.download.FileDownloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoadImage extends Activity {
	private TextView resultView;
	private ProgressBar progressBar;
	private Handler handler = new UIHander();
	private int window_width, window_height;// 控件宽度
	private DragImageView dragImageView;// 自定义控件
	private int state_height;// 状态栏的高度

	private ViewTreeObserver viewTreeObserver;

	private final class UIHander extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				progressBar.setProgress(size);
				float num = (float) progressBar.getProgress() / (float) progressBar.getMax();
				int result = (int) (num * 100);
				resultView.setText(result + "%");
				if (progressBar.getProgress() == progressBar.getMax()) {
					Toast.makeText(getApplicationContext(), R.string.success, 1).show();
					dragImageView.setImageBitmap(AppCache.getrealImage());
					resultView.setVisibility(View.INVISIBLE);
				}

				break;

			case -1:
				Toast.makeText(getApplicationContext(), R.string.error, 1).show();

				break;
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadimage);
		progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
		resultView = (TextView) this.findViewById(R.id.resultView);
		String path = getIntent().getExtras().getString("path");
		load(C.dir.qq + path);
		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();

		dragImageView = (DragImageView) findViewById(R.id.div_main);
		Bitmap bmp = AppCache.getImage(path);
		// 设置图片
		dragImageView.setImageBitmap(bmp);
		dragImageView.setmActivity(this);// 注入Activity.
		/** 测量状态栏高度 **/
		viewTreeObserver = dragImageView.getViewTreeObserver();
		viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				if (state_height == 0) {
					// 获取状况栏高度
					Rect frame = new Rect();
					getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
					state_height = frame.top;
					dragImageView.setScreen_H(window_height - state_height);
					dragImageView.setScreen_W(window_width);
				}

			}
		});

	}

	public void load(String path) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File saveDir = new File(Environment.getExternalStorageDirectory() + C.dir.images + "/");
			download(path, saveDir);
		} else {
			Toast.makeText(getApplicationContext(), R.string.sdcarderror, 1).show();
		}

	}

	/*
	 * 由于用户的输入事件(点击button, 触摸屏幕....)是由主线程负责处理的，如果主线程处于工作状态，
	 * 此时用户产生的输入事件如果没能在5秒内得到处理，系统就会报“应用无响应”错误。
	 * 所以在主线程里不能执行一件比较耗时的工作，否则会因主线程阻塞而无法处理用户的输入事件，
	 * 导致“应用无响应”错误的出现。耗时的工作应该在子线程里执行。
	 */
	private DownloadTask task;

	/**
	 * 退出下载
	 */
	public void exit() {
		if (task != null)
			task.exit();
	}

	private void download(String path, File saveDir) {// 运行在主线程
		task = new DownloadTask(path, saveDir);
		new Thread(task).start();
	}

	/*
	 * UI控件画面的重绘(更新)是由主线程负责处理的，如果在子线程中更新UI控件的值，更新后的值不会重绘到屏幕上
	 * 一定要在主线程里更新UI控件的值，这样才能在屏幕上显示出来，不能在子线程中更新UI控件的值
	 */
	private final class DownloadTask implements Runnable {
		private String path;
		private File saveDir;
		private FileDownloader loader;

		public DownloadTask(String path, File saveDir) {
			this.path = path;
			this.saveDir = saveDir;
		}

		/**
		 * 退出下载
		 */
		public void exit() {
			if (loader != null)
				loader.exit();
		}

		public void run() {
			try {
				loader = new FileDownloader(getApplicationContext(), path, saveDir, 3);
				progressBar.setMax(loader.getFileSize());// 设置进度条的最大刻度
				loader.download(new DownloadProgressListener() {
					public void onDownloadSize(int size) {
						Message msg = new Message();
						msg.what = 1;
						msg.getData().putInt("size", size);
						handler.sendMessage(msg);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				handler.sendMessage(handler.obtainMessage(-1));
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			finish();
		}
		return false;

	}

}