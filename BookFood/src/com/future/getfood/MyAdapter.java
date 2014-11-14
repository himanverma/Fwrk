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

import com.future.foodimg.ImageLoader;
import com.future.getfood.R;
import com.future.listscroll.InfiniteScrollAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

public class MyAdapter extends InfiniteScrollAdapter {

	private ArrayList<HashMap<String, String>> items;
	public static boolean bool = true;
	Context ctx;
	int pos;
	ArrayList<String>list;
	ArrayList<String>namelist;
	ArrayList<String>namelist1;
	ArrayList<String>pricelist;
	ArrayList<String>imglist;
	ArrayList<String>addresslist;
	ArrayList<String>mobilelist;
	ArrayList<String>phonelist;
	ArrayList<String>chefphoto;
    ImageLoader il;
	public MyAdapter(Context context,ArrayList<String>ll,ArrayList<String>nll,ArrayList<String>pll,ArrayList<String>imll,
			ArrayList<String>adml,ArrayList<String>poll,ArrayList<String>moll,ArrayList<String>pcholl) {
		super(context);
		ctx = context;
		items = new ArrayList<HashMap<String, String>>();
		list=new ArrayList<String>();
		namelist1=new ArrayList<String>();
		
		list=ll;
		namelist=nll;
		pricelist=pll;
		namelist1.addAll(list);
		imglist=imll;
		addresslist=adml;
		mobilelist=moll;
		phonelist=poll;
		chefphoto=pcholl;
		il=new ImageLoader(ctx);
	}

	@Override
	public ArrayList getItems() {
		return list;
	}

	@Override
	public void addItems(Collection items) {
		if (items.size() > 0) {
			this.list.addAll(items);
		} else {
			super.setDoneLoading();
		}
		notifyDataSetChanged();
		
	}

	@Override
	public Object getRealItem(int position) {
		
		return list.get(position);
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
		 TextView price_dd=((TextView) v.findViewById(R.id.textView4));
		Button btn = (Button) v.findViewById(R.id.button1);
		Button send = (Button) v.findViewById(R.id.button2);
		final RelativeLayout rel = (RelativeLayout) v.findViewById(R.id.rel22);
		
		final RelativeLayout rel1 = (RelativeLayout) v.findViewById(R.id.rr1);
		
		final RelativeLayout rel2 = (RelativeLayout) v.findViewById(R.id.rr2);
		
		CheckBox chk1=(CheckBox) v.findViewById(R.id.checkBox1);
		CheckBox chk2=(CheckBox) v.findViewById(R.id.checkBox2);
		CheckBox chk3=(CheckBox) v.findViewById(R.id.checkBox3);
		il.DisplayImage(imglist.get(position), dish_img);
		// Log.e("nnn", list.get(position));
		 dish_name.setText(list.get(position));
		 user_name.setText("By "+namelist.get(position));
		 price_dd.setText("Price: Rs"+pricelist.get(position));
		rel1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				if (bool == false) {

					rel.setVisibility(View.GONE);
					bool = true;

				} else {

					rel.setVisibility(View.VISIBLE);
					bool = false;
					
				}

			}
		});

		rel2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				if (bool == false) {

					rel.setVisibility(View.GONE);
					bool = true;

				} else {

					rel.setVisibility(View.VISIBLE);
					bool = false;
					
				}

			}
		});
		user_name.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(ctx, ChefProfile.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				in.putExtra("photo", chefphoto.get(position));
				in.putExtra("name", namelist.get(position));
				in.putExtra("address", addresslist.get(position));
				in.putExtra("mob", mobilelist.get(position));
				in.putExtra("phone", phonelist.get(position));
				
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

	// Filter Class
			public void filter(String charText) {
				charText = charText.toLowerCase(Locale.getDefault());
				list.clear();
				if (charText.length() == 0) {
					list.addAll(namelist1);
				} else {
					for (int i = 0; i < namelist1.size(); i++) {
						if (namelist1.get(i)
								.toLowerCase(Locale.getDefault())
								.contains(charText)) {
							list.add(namelist1.get(i));
						}
					}
				}
				notifyDataSetChanged();
			}
}
