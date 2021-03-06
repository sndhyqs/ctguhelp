/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.ctgu.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ctgu.base.C;

public class IO {

	public static String inputSreamToString(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			if (C.debug == true)
				Log.i("IO", "文件读取中");
			while ((line = br.readLine()) != null) {

				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (C.debug == true)
			Log.i("IO", "文件读完毕");

		return sb.toString();
	}

	public static void saveToPackage(Context context, String content, String filename) {
		try {
			File file = new File(context.getCacheDir(), filename);
			if (C.debug == true)
				Log.i("地址", context.getCacheDir().toString());
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(content.getBytes());
			outStream.close();
			return;
		} catch (IOException e) {
			Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
		}
	}

	public static String readFile(File file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {

				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String string = sb.toString();
		return string;
	}

	public static String saveToSDka(Bitmap bitmap, Context context, String filename) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			Toast.makeText(context, "SD卡不可用", Toast.LENGTH_SHORT).show();
		}

		File dir = new File(Environment.getExternalStorageDirectory() + C.dir.images);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String realFileName = dir + "/" + filename;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(realFileName);
			if (bitmap != null) {
				if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
					fileOutputStream.flush();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return realFileName;
	}
}
