package com.example.sanjicache;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sanjicache.utils.CustomBitmapUtils;

/**
 * 程序接入口
 * 
 **/
public class MainActivity extends Activity {
	private ListView list;
	private Button btn;
	// 进入工具来进行管理
	private CustomBitmapUtils utils;
	// 圖片的存放位置（网络），头
	private static final String BASE_URL = "http://192.168.23.1:8080/hb";
	// 尾
	String[] urls = { BASE_URL + "/hb001.jpg", BASE_URL + "/hb002.jpg",
			BASE_URL + "/hb003.jpg", BASE_URL + "/hb004.jpg",
			BASE_URL + "/hb005.jpg", BASE_URL + "/hb006.jpg",
			BASE_URL + "/hb007.jpg", BASE_URL + "/hb008.jpg",
			BASE_URL + "/hb009.jpg", BASE_URL + "/hb010.jpg",
			BASE_URL + "/hb011.jpg", BASE_URL + "/hb012.jpg",
			BASE_URL + "/hb013.jpg", BASE_URL + "/hb014.jpg",
			BASE_URL + "/hb015.jpg", BASE_URL + "/hb016.jpg",
			BASE_URL + "/hb017.jpg", BASE_URL + "/hb018.jpg",
			BASE_URL + "/hb019.jpg", BASE_URL + "/hb020.jpg",
			BASE_URL + "/hb021.jpg", BASE_URL + "/hb022.jpg",
			BASE_URL + "/hb023.jpg", BASE_URL + "/hb024.jpg",
			BASE_URL + "/hb025.jpg", BASE_URL + "/hb026.jpg",
			BASE_URL + "/hb027.jpg", BASE_URL + "/hb028.jpg",
			BASE_URL + "/hb029.jpg", BASE_URL + "/hb030.jpg" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		btn = (Button) findViewById(R.id.btn_load);
		utils = new CustomBitmapUtils();
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyAdapter adapter = new MyAdapter();
				list.setAdapter(adapter);
			}
		});
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return urls.length;
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return urls[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (contentView == null) {
				contentView = View.inflate(MainActivity.this,
						R.layout.listview, null);
				holder = new ViewHolder();
				holder.ivpic = (ImageView) contentView
						.findViewById(R.id.imageView);
				contentView.setTag(holder);
			} else {
				holder = (ViewHolder) contentView.getTag();
			}
			utils.display(holder.ivpic, urls[position]);
			return contentView;
		}

	}

	class ViewHolder {
		ImageView ivpic;
	}
}
