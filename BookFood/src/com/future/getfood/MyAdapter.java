package com.future.getfood;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.future.getfood.R;
import com.future.listscroll.InfiniteScrollAdapter;

import java.util.ArrayList;
import java.util.Collection;

public class MyAdapter extends InfiniteScrollAdapter {

	private ArrayList<String> items;
	public static boolean bool = true;
	Context ctx;
	int pos;
	private int mSelectedPos = -1;

	public MyAdapter(Context context) {
		super(context);
		ctx = context;
		items = new ArrayList<String>();
	}

	@Override
	public ArrayList getItems() {
		return items;
	}

	@Override
	public void addItems(Collection items) {
		if (items.size() > 0) {
			this.items.addAll(items);
		} else {
			super.setDoneLoading();
		}
		notifyDataSetChanged();
		Log.e("ggg", "heer");
	}

	@Override
	public Object getRealItem(int position) {
		Log.e("gggbbb", "heer");
		return items.get(position);
	}

	public void SetSelectedPosition(int position) {
		mSelectedPos = position;
	}

	@Override
	public View getRealView(LayoutInflater inflater, final int position,
			View convertView, ViewGroup parent) {
		View v = inflater.inflate(R.layout.dishes_item, null);
		TextView dish_name=((TextView) v.findViewById(R.id.dishname));
		ImageView dish_img=(ImageView) v.findViewById(R.id.imageView1);
		TextView user_name=((TextView) v.findViewById(R.id.textView1));
		 RatingBar rb=(RatingBar) v.findViewById(R.id.ratingBar1);
		 TextView rate_num=((TextView) v.findViewById(R.id.textView2));
		 TextView delivery_time=((TextView) v.findViewById(R.id.textView3));
		 
		Button btn = (Button) v.findViewById(R.id.button1);
		Button send = (Button) v.findViewById(R.id.button2);
		final RelativeLayout rel = (RelativeLayout) v.findViewById(R.id.rel22);
		CheckBox chk1=(CheckBox) v.findViewById(R.id.checkBox1);
		CheckBox chk2=(CheckBox) v.findViewById(R.id.checkBox2);
		CheckBox chk3=(CheckBox) v.findViewById(R.id.checkBox3);
		
		 
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				if (bool == false) {

					rel.setVisibility(View.GONE);
					bool = true;

				} else {

					rel.setVisibility(View.VISIBLE);
					bool = false;
					SetSelectedPosition(position);
				}

			}
		});

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(ctx, NextAct.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				in.putExtra("text", items.get(position));
				ctx.startActivity(in);

			}
		});
		
		bool = true;
		return v;
	}

	@Override
	public View getLoadingView(LayoutInflater inflater, ViewGroup parent) {
		return inflater.inflate(R.layout.list_loading, null);
	}

	
}
