package com.example.sanjicache.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class LocalCacheUtils {
	// �����ļ������·��
	public static final String FILE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/test/pics";

	// �ӱ���SD���л�ȡ����ͼƬ
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

	// �򱾵�SD��д����ͼƬ
	public void setBitmap2Local(String url, Bitmap bitmap) {
		try {
			// �ļ�������
			String fileName = MD5Encoder.encode(url);
			// �����ļ�����ָ���·�����ļ�������filename
			File file = new File(FILE_PATH, fileName);
			// file��ʵ��ͼƬ�����ĸ���File���ļ��У��ж����ļ����Ƿ���ڡ���������ڴ����ļ���
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				// �ļ��в�����,�����ļ���
				fileParent.mkdirs();
			}
			// ��ͼƬ���浽����
			bitmap.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(file));
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
	}

}
