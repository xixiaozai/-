package com.example.sanjicache.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

//����
public class MemoryCacheUtils {
	/*
	 * ����mapĬ����ǿ���ã�������JVM�����������յ�ʱ�򲻻����map������
	 */
	// private HashMap<String, Bitmap> map = new HashMap<String, Bitmap>();
	// �����õ�ʵ��,���ڴ治��ʱ�����������������ȿ��ǻ���
	// private HashMap<String, SoftReference<Bitmap>> mSoftReferenceMap = new
	// HashMap<String, SoftReference<Bitmap>>();
	// LruCache
	private LruCache<String, Bitmap> lruCache;

	public MemoryCacheUtils() {
		// lruCache��������ڴ�һ���ǰ�׿ϵͳ�����ÿ��Ӧ���ڴ�Ĵ�С��Ĭ��ÿ��16�ף�
		// ��ȡ��ǰӦ�ó������е��ڴ��С
		long mCurrentMemory = Runtime.getRuntime().maxMemory();
		// ���ø�LruCache������ڴ�
		int maxSize = (int) (mCurrentMemory / 8);
		lruCache = new LruCache<String, Bitmap>(maxSize) {
			protected int sizeOf(String key, Bitmap value) {
				// ��ȡÿ��ͼƬ��ռ�ڴ�Ĵ���
				// ���㷽���ǣ�ͼƬ��ʾ�Ŀ�����ص���߶����ص�ĳ˻�
				int byteCount = value.getRowBytes() * value.getHeight();// ��ȡͼƬռ���ڴ�Ĵ�С
				return byteCount;
			}
		};
	}

	// ���ڴ��ж�ȡͼƬ
	public Bitmap getBitmapFromMemory(String url) {

		// Bitmap bitmap = map.get(url);
		// SoftReference<Bitmap> softReference = mSoftReferenceMap.get(url);
		// Bitmap bitmap2 = softReference.get();
		// ��������Android2.3�Ժ�Ͳ��Ƽ�ʹ���ˣ�Google�Ƽ�ʹ��lruCache
		// LRU--least recently use
		// �������ʹ��,���ڴ������һ���Ĵ�С�ڣ���������ڴ��С���ͻ������ͷ��������ʹ�õ���Щ����
		Bitmap bitmap = lruCache.get(url);
		return bitmap;
	}

	// Ȼ���ٰ�ͼƬ���浽�ڴ�����
	public void setBitmap2Memory(String url, Bitmap bitmap) {
		// ���ڴ������ã�key,value����ʽ�������뵽HashMap
		// map.put(url, bitmap);
		// ���������õ�map��
		// SoftReference<Bitmap> mSoftReference = new
		// SoftReference<Bitmap>(bitmap);
		// mSoftReferenceMap.put(url, mSoftReference);
		//lruCache.put(url, bitmap);
	}

}
