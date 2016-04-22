package com.example.sanjicache.utils;

import com.example.sanjicache.R;

import android.graphics.Bitmap;
import android.widget.ImageView;
/**
 * �Զ��幤����
 * */
public class CustomBitmapUtils {
    private Bitmap bitmap;
    
    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheutils;
    private MemoryCacheUtils memoryCacheUtils;
    
    //����Щ������ĳ�ʼ��
    public CustomBitmapUtils(){
    	netCacheUtils=new NetCacheUtils();
    	localCacheutils=new LocalCacheUtils();
    	memoryCacheUtils=new MemoryCacheUtils();
    }
    /*
     * ����ͼƬ���Ѹղ�������ַ��ͼƬ��ʾ��ivpic�Ŀؼ���
     * **/
	public void display(ImageView ivpic, String url) {
		// TODO Auto-generated method stub
		//����Ĭ�ϵ�ͼƬ
		ivpic.setImageResource(R.drawable.ic_launcher);
		/*
		 * �������Բ�ͬ�Ļ��淽ʽ���д���
		 * **/
		//1.�ڴ滺��
		bitmap=memoryCacheUtils.getBitmapFromMemory(url);
		if(bitmap!=null)
		{
			ivpic.setImageBitmap(bitmap);
			System.out.print("���ڴ滺�����ͼƬ");
			return;
		}
		//2.���ش��̻���
		bitmap=localCacheutils.getBitmapFromLocal(url);
		if(bitmap!=null)
		{
		ivpic.setImageBitmap(bitmap);
		System.out.print("�ӱ���SD���м���ͼƬ");
		//��ͼƬ���浽�ڴ�
		memoryCacheUtils.setBitmap2Memory(url,bitmap);
		return;
		}
		//���绺�� 
		netCacheUtils.getBitmapFromNet(ivpic,url);
		
	}

}
