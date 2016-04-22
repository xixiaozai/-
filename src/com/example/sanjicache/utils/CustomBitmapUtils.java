package com.example.sanjicache.utils;

import com.example.sanjicache.R;

import android.graphics.Bitmap;
import android.widget.ImageView;
/**
 * 自定义工具类
 * */
public class CustomBitmapUtils {
    private Bitmap bitmap;
    
    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheutils;
    private MemoryCacheUtils memoryCacheUtils;
    
    //对这些工具类的初始化
    public CustomBitmapUtils(){
    	netCacheUtils=new NetCacheUtils();
    	localCacheutils=new LocalCacheUtils();
    	memoryCacheUtils=new MemoryCacheUtils();
    }
    /*
     * 加载图片，把刚才申明地址的图片显示到ivpic的控件上
     * **/
	public void display(ImageView ivpic, String url) {
		// TODO Auto-generated method stub
		//设置默认的图片
		ivpic.setImageResource(R.drawable.ic_launcher);
		/*
		 * 接下来对不同的缓存方式进行处理
		 * **/
		//1.内存缓存
		bitmap=memoryCacheUtils.getBitmapFromMemory(url);
		if(bitmap!=null)
		{
			ivpic.setImageBitmap(bitmap);
			System.out.print("从内存缓存加载图片");
			return;
		}
		//2.本地磁盘缓存
		bitmap=localCacheutils.getBitmapFromLocal(url);
		if(bitmap!=null)
		{
		ivpic.setImageBitmap(bitmap);
		System.out.print("从本地SD卡中加载图片");
		//把图片保存到内存
		memoryCacheUtils.setBitmap2Memory(url,bitmap);
		return;
		}
		//网络缓存 
		netCacheUtils.getBitmapFromNet(ivpic,url);
		
	}

}
