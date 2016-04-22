package com.example.sanjicache.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

//网络缓存工具类
public class NetCacheUtils {
	private LocalCacheUtils localCacheUtils;
	private MemoryCacheUtils memoryCacheUtils;

	public NetCacheUtils() {
		localCacheUtils = new LocalCacheUtils();
		memoryCacheUtils = new MemoryCacheUtils();
	}

	// 从网络下载图片
	public void getBitmapFromNet(ImageView ivpic, String url) {
		// TODO Auto-generated method stub
		// 访问网络的操作一定要在子线程中进行，采用异步任务实现
		MyAsyncTask task = new MyAsyncTask();
		task.execute(ivpic, url);
	}

	// 异步线程
	// 里面有三个参数，第一个是异步任务执行的时候，通过execute穿过来的参数；第二个是更新进度；第三个是异步任务执行以后返回的结果
	private class MyAsyncTask extends AsyncTask<Object, Void, Bitmap>

	{
		private ImageView ivpic;
		private String url;

		// 耗时任务执行之前--主线程
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// 后台执行的任务
		@Override
		protected Bitmap doInBackground(Object... params) {
			// TODO Auto-generated method stub
			// 执行异步任务的时候，将URL传过来
			ivpic = (ImageView) params[0];
			url = (String) params[1];
			Bitmap bitmap = downloadBitmap(url);
			// 为了保证ImageView控件和URL相对应，给ImageView设定一个标记
			ivpic.setTag(url);// 关联ivpic和URL
			return bitmap;
		}

		// 更新线程
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		// 耗时任务执行之后--主线程
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			String mCurrentUrl = (String) ivpic.getTag();
			if (url.equals(mCurrentUrl)) {
				ivpic.setImageBitmap(result);
				System.out.print("从网络获取图片");
				// 从网络加载完成以后，本地和内存中个保存一分
				localCacheUtils.setBitmap2Local(url, result);
				// memoryCacheUtils.setBitmap2Memory(url, result);
			}
		}

	}

	// 下载网络图片
	private Bitmap downloadBitmap(String url) {
		// TODO Auto-generated method stub
		HttpURLConnection conn = null;
		try {
			URL mURL = new URL(url);
			// 打开httpurlconnetction链接
			conn = (HttpURLConnection) mURL.openConnection();
			// 设置参数
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			// 开启连接
			conn.connect();

			// 获得响应吗
			int code = conn.getResponseCode();
			if (code == 200) {
				System.out.print("连接成功");
				// 获取网络反应回来的输入流
				InputStream is = conn.getInputStream();

				// 链接成功，设置图片的压缩参数，将图片进行压缩
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;// 将图片的宽高都压缩为原来的一半,在开发中此参数需要根据图片展示的大小来确定,否则可能展示的不正常
				options.inPreferredConfig = Bitmap.Config.RGB_565;// 这个是压缩的最小
				Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);// 压缩后的图片
				return bitmap;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 断开链接
			conn.disconnect();
		}
		return null;
	}
}
