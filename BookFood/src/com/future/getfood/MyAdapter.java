package com.future.getfood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	private Typeface tf1,tf2,tf3,tf4;
	String chk_value = "no";
	ArrayList<String> list;
	ArrayList<String> namelist;
	ArrayList<String> namelist1;
	ArrayList<String> pricelist;
	ArrayList<String> imglist;
	ArrayList<String> addresslist;
	ArrayList<String> mobilelist;
	ArrayList<String> phonelist;
	ArrayList<String> chefphoto;
	ArrayList<String> chefid;
	ArrayList<String> chefrate;
	ImageLoader il;
	SessionManager sess;
	String user_id;

	public MyAdapter(Context context, ArrayList<String> ll,
			ArrayList<String> nll, ArrayList<String> pll,
			ArrayList<String> imll, ArrayList<String> adml,
			ArrayList<String> poll, ArrayList<String> moll,
			ArrayList<String> pcholl,ArrayList<String> idll,ArrayList<String> rll) {
		super(context);
		ctx = context;
		items = new ArrayList<HashMap<String, String>>();
		list = new ArrayList<String>();
		namelist1 = new ArrayList<String>();

		list = ll;
		namelist = nll;
		pricelist = pll;
		namelist1.addAll(list);
		imglist = imll;
		addresslist = adml;
		mobilelist = moll;
		phonelist = poll;
		chefphoto = pcholl;
		chefid=idll;
		chefrate=rll;
		il = new ImageLoader(ctx);

		sess = new SessionManager(ctx);
		HashMap<String, String> map = sess.getUserDetails();
		user_id = map.get(SessionManager.KEY_ID);
		tf1 = Typeface.createFromAsset(ctx.getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(ctx.getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(ctx.getAssets(), "Roboto-Thin.ttf");
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
		TextView dish_name = ((TextView) v.findViewById(R.id.dishname));
		ImageView dish_img = (ImageView) v.findViewById(R.id.imageView1);
		TextView user_name = ((TextView) v.findViewById(R.id.textView1));
		RatingBar rb = (RatingBar) v.findViewById(R.id.ratingBar1);
		TextView rate_num = ((TextView) v.findViewById(R.id.textView2));
		TextView delivery_time = ((TextView) v.findViewById(R.id.textView3));
		TextView price_dd = ((TextView) v.findViewById(R.id.textView4));

		final Button send = (Button) v.findViewById(R.id.button2);

		final RelativeLayout rel = (RelativeLayout) v.findViewById(R.id.rel22);
		final RelativeLayout rel1 = (RelativeLayout) v.findViewById(R.id.rr1);
		final RelativeLayout rel2 = (RelativeLayout) v.findViewById(R.id.rr2);

		final CheckBox chk1 = (CheckBox) v.findViewById(R.id.checkBox1);
		final CheckBox chk2 = (CheckBox) v.findViewById(R.id.checkBox2);
		final CheckBox chk3 = (CheckBox) v.findViewById(R.id.checkBox3);
		chk1.setChecked(true);
		chk_value = "4 Roti+Rice";

		// set value
		il.DisplayImage(imglist.get(position), dish_img);
		dish_name.setText(list.get(position));
		dish_name.setTypeface(tf1);
		user_name.setText("By " + namelist.get(position));
		user_name.setTypeface(tf3);
		price_dd.setText("Price: Rs" + pricelist.get(position));
		price_dd.setTypeface(tf1);
		delivery_time.setTypeface(tf4);
		rb.setRating(Float.parseFloat(chefrate.get(position)));
		rate_num.setText("("+chefrate.get(position)+")");
		rate_num.setTypeface(tf4);
		chk1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (chk1.isChecked()) {

					chk_value = "4 Roti+Rice";
					chk2.setChecked(false);
					chk3.setChecked(false);
					
				}
			}
		});

		chk2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (chk2.isChecked()) {

					chk_value = "Full Rice";
					chk1.setChecked(false);
					chk3.setChecked(false);
					
				}
			}
		});

		chk3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (chk3.isChecked()) {

					chk_value = "6 Roti";
					chk1.setChecked(false);
					chk2.setChecked(false);
					
				}
			}
		});

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (chk_value.equals("no")) {

					Toast.makeText(ctx, "please select option!", 5000).show();

				} else {

					if (user_id.equals("0")) {

						Intent in = new Intent(ctx, UserLogin.class);
						in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						in.putExtra("dish", list.get(position));
						in.putExtra("chk", chk_value);
						in.putExtra("price", pricelist.get(position));
						
						ctx.startActivity(in);
					
					
					} else {
						
						Intent in = new Intent(ctx, OrderDishes.class);
						in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						in.putExtra("dish", list.get(position));
						in.putExtra("chk", chk_value);
						in.putExtra("price", pricelist.get(position));
						
						ctx.startActivity(in);
					}
				}
			}
		});

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
				in.putExtra("chefid", chefid.get(position));
				in.putExtra("rate", chefrate.get(position));
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
				if (namelist1.get(i).toLowerCase(Locale.getDefault())
						.contains(charText)) {
					list.add(namelist1.get(i));
				}
			}
		}
		notifyDataSetChanged();
	}
}
