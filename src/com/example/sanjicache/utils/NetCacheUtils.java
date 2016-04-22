package com.example.sanjicache.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

//���绺�湤����
public class NetCacheUtils {
	private LocalCacheUtils localCacheUtils;
	private MemoryCacheUtils memoryCacheUtils;

	public NetCacheUtils() {
		localCacheUtils = new LocalCacheUtils();
		memoryCacheUtils = new MemoryCacheUtils();
	}

	// ����������ͼƬ
	public void getBitmapFromNet(ImageView ivpic, String url) {
		// TODO Auto-generated method stub
		// ��������Ĳ���һ��Ҫ�����߳��н��У������첽����ʵ��
		MyAsyncTask task = new MyAsyncTask();
		task.execute(ivpic, url);
	}

	// �첽�߳�
	// ������������������һ�����첽����ִ�е�ʱ��ͨ��execute�������Ĳ������ڶ����Ǹ��½��ȣ����������첽����ִ���Ժ󷵻صĽ��
	private class MyAsyncTask extends AsyncTask<Object, Void, Bitmap>

	{
		private ImageView ivpic;
		private String url;

		// ��ʱ����ִ��֮ǰ--���߳�
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// ��ִ̨�е�����
		@Override
		protected Bitmap doInBackground(Object... params) {
			// TODO Auto-generated method stub
			// ִ���첽�����ʱ�򣬽�URL������
			ivpic = (ImageView) params[0];
			url = (String) params[1];
			Bitmap bitmap = downloadBitmap(url);
			// Ϊ�˱�֤ImageView�ؼ���URL���Ӧ����ImageView�趨һ�����
			ivpic.setTag(url);// ����ivpic��URL
			return bitmap;
		}

		// �����߳�
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		// ��ʱ����ִ��֮��--���߳�
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			String mCurrentUrl = (String) ivpic.getTag();
			if (url.equals(mCurrentUrl)) {
				ivpic.setImageBitmap(result);
				System.out.print("�������ȡͼƬ");
				// �������������Ժ󣬱��غ��ڴ��и�����һ��
				localCacheUtils.setBitmap2Local(url, result);
				// memoryCacheUtils.setBitmap2Memory(url, result);
			}
		}

	}

	// ��������ͼƬ
	private Bitmap downloadBitmap(String url) {
		// TODO Auto-generated method stub
		HttpURLConnection conn = null;
		try {
			URL mURL = new URL(url);
			// ��httpurlconnetction����
			conn = (HttpURLConnection) mURL.openConnection();
			// ���ò���
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			// ��������
			conn.connect();

			// �����Ӧ��
			int code = conn.getResponseCode();
			if (code == 200) {
				System.out.print("���ӳɹ�");
				// ��ȡ���練Ӧ������������
				InputStream is = conn.getInputStream();

				// ���ӳɹ�������ͼƬ��ѹ����������ͼƬ����ѹ��
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;// ��ͼƬ�Ŀ�߶�ѹ��Ϊԭ����һ��,�ڿ����д˲�����Ҫ����ͼƬչʾ�Ĵ�С��ȷ��,�������չʾ�Ĳ�����
				options.inPreferredConfig = Bitmap.Config.RGB_565;// �����ѹ������С
				Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);// ѹ�����ͼƬ
				return bitmap;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// �Ͽ�����
			conn.disconnect();
		}
		return null;
	}
}
