package com.example.sanjicache.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

//缓存
public class MemoryCacheUtils {
	/*
	 * 由于map默认是强引用，所有在JVM进行垃圾回收的时候不会回收map的引用
	 */
	// private HashMap<String, Bitmap> map = new HashMap<String, Bitmap>();
	// 软引用的实例,在内存不够时，垃圾回收器会优先考虑回收
	// private HashMap<String, SoftReference<Bitmap>> mSoftReferenceMap = new
	// HashMap<String, SoftReference<Bitmap>>();
	// LruCache
	private LruCache<String, Bitmap> lruCache;

	public MemoryCacheUtils() {
		// lruCache最大允许内存一般是安卓系统分配给每个应用内存的大小（默认每个16兆）
		// 获取当前应用程序运行的内存大小
		long mCurrentMemory = Runtime.getRuntime().maxMemory();
		// 设置给LruCache的最大内存
		int maxSize = (int) (mCurrentMemory / 8);
		lruCache = new LruCache<String, Bitmap>(maxSize) {
			protected int sizeOf(String key, Bitmap value) {
				// 获取每张图片所占内存的大侠
				// 计算方法是：图片显示的宽度像素点与高度像素点的乘积
				int byteCount = value.getRowBytes() * value.getHeight();// 获取图片占用内存的大小
				return byteCount;
			}
		};
	}

	// 从内存中读取图片
	public Bitmap getBitmapFromMemory(String url) {

		// Bitmap bitmap = map.get(url);
		// SoftReference<Bitmap> softReference = mSoftReferenceMap.get(url);
		// Bitmap bitmap2 = softReference.get();
		// 软引用在Android2.3以后就不推荐使用了，Google推荐使用lruCache
		// LRU--least recently use
		// 最近最少使用,将内存控制在一定的大小内，超过这个内存大小，就会优先释放最近最少使用的那些东东
		Bitmap bitmap = lruCache.get(url);
		return bitmap;
	}

	// 然后再把图片保存到内存中区
	public void setBitmap2Memory(String url, Bitmap bitmap) {
		// 向内存中设置，key,value的形式，首先想到HashMap
		// map.put(url, bitmap);
		// 保存软引用到map中
		// SoftReference<Bitmap> mSoftReference = new
		// SoftReference<Bitmap>(bitmap);
		// mSoftReferenceMap.put(url, mSoftReference);
		//lruCache.put(url, bitmap);
	}

}
