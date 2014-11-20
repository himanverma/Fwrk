package com.future.getfood;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import com.future.foodimg.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class CustomGrid extends BaseAdapter {
	private Context mContext;
	ArrayList<String> dishname = new ArrayList<String>();
	ArrayList<String> dishname1 = new ArrayList<String>();
	ArrayList<String> dishimg = new ArrayList<String>();
	ImageLoader il;
	TextView textView;
	ImageView imageView;
	private Typeface tf1;
	private static LayoutInflater inflater = null;

	public CustomGrid(Context c, ArrayList<String> name, ArrayList<String> Image) {
		mContext = c;
		this.dishimg = Image;
		this.dishname = name;
        dishname1.addAll(dishname);
		il = new ImageLoader(mContext);
		tf1 = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Bold.ttf");
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dishname.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder {
		TextView tv;
		ImageView img;
	}

	
	// Filter Class
				public void filter(String charText) {
					charText = charText.toLowerCase(Locale.getDefault());
					dishname.clear();
					if (charText.length() == 0) {
						dishname.addAll(dishname1);
					} else {
						for (int i = 0; i < dishname1.size(); i++) {
							if (dishname1.get(i)
									.toLowerCase(Locale.getDefault())
									.contains(charText)) {
								dishname.add(dishname1.get(i));
							}
						}
					}
					notifyDataSetChanged();
				}
				
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = new Holder();
		View rowView;

		rowView = inflater.inflate(R.layout.grid_single, null);
		holder.tv = (TextView) rowView.findViewById(R.id.grid_text);
		holder.img = (ImageView) rowView.findViewById(R.id.grid_image);

		holder.tv.setText(dishname.get(position));
		holder.tv.setTypeface(tf1);
		il.DisplayImage(dishimg.get(position), holder.img);
		
		
	
		return rowView;
	}
	
	
}
