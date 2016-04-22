package com.example.sanjicache.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class LocalCacheUtils {
	// 设置文件保存的路径
	public static final String FILE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/test/pics";

	// 从本地SD卡中获取网络图片
	public Bitmap getBitmapFromLocal(String url) {
		// TODO Auto-generated method stub
		try {
			String fileName = MD5Encoder.encode(url);
			File file = new File(FILE_PATH, fileName);
			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
						file));
				return bitmap;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	// 向本地SD卡写网络图片
	public void setBitmap2Local(String url, Bitmap bitmap) {
		try {
			// 文件的名字
			String fileName = MD5Encoder.encode(url);
			// 创建文件流，指向该路径，文件名叫做filename
			File file = new File(FILE_PATH, fileName);
			// file其实是图片，他的父级File是文件夹，判断下文件夹是否存在、如果不存在创建文件夹
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				// 文件夹不存在,创建文件夹
				fileParent.mkdirs();
			}
			// 将图片保存到本地
			bitmap.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(file));
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
	}

}
